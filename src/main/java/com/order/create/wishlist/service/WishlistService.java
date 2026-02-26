package com.order.create.wishlist.service;

import com.order.create.wishlist.dto.*;
import com.order.create.wishlist.entity.WishlistItem;
import com.order.create.wishlist.kafka.WishlistEventProducer;
import com.order.create.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enhancement #12 - Wishlist & Favorites Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WishlistService {

    private final WishlistRepository wishlistRepo;
    private final WishlistEventProducer eventProducer;

    /** Add a product to customer's wishlist */
    @Transactional
    public WishlistResponse addToWishlist(WishlistRequest req) {
        log.info("[WISHLIST] Adding product={} to wishlist for customer={}", req.getProductId(), req.getCustomerId());

        if (wishlistRepo.existsByCustomerIdAndProductId(req.getCustomerId(), req.getProductId())) {
            throw new RuntimeException("Product already in wishlist: " + req.getProductId());
        }

        WishlistItem item = WishlistItem.builder()
                .customerId(req.getCustomerId())
                .productId(req.getProductId())
                .productName(req.getProductName())
                .productCategory(req.getProductCategory())
                .originalPrice(req.getPrice())
                .productImageUrl(req.getProductImageUrl())
                .notes(req.getNotes())
                .notifyOnPriceDrop(req.isNotifyOnPriceDrop())
                .notifyOnBackInStock(req.isNotifyOnBackInStock())
                .build();

        item = wishlistRepo.save(item);
        eventProducer.publishWishlistAdded(req.getCustomerId(), req.getProductId(), req.getProductName());
        log.info("[WISHLIST] Product added to wishlist — id={}", item.getId());
        return toResponse(item, req.getPrice(), true);
    }

    /** Get all wishlist items for a customer */
    public List<WishlistResponse> getWishlist(String customerId) {
        log.info("[WISHLIST] Fetching wishlist for customer={}", customerId);
        return wishlistRepo.findByCustomerId(customerId)
                .stream().map(i -> toResponse(i, i.getOriginalPrice(), true))
                .collect(Collectors.toList());
    }

    /** Remove a product from wishlist */
    @Transactional
    public void removeFromWishlist(String customerId, String productId) {
        log.info("[WISHLIST] Removing product={} from wishlist for customer={}", productId, customerId);
        wishlistRepo.deleteByCustomerIdAndProductId(customerId, productId);
    }

    /** Check if product is in wishlist */
    public boolean isInWishlist(String customerId, String productId) {
        return wishlistRepo.existsByCustomerIdAndProductId(customerId, productId);
    }

    /** Move wishlist item to cart — removes from wishlist */
    @Transactional
    public WishlistResponse moveToCart(String customerId, String productId) {
        log.info("[WISHLIST] Moving product={} to cart for customer={}", productId, customerId);
        WishlistItem item = wishlistRepo.findByCustomerIdAndProductId(customerId, productId)
                .orElseThrow(() -> new RuntimeException("Wishlist item not found"));
        WishlistResponse resp = toResponse(item, item.getOriginalPrice(), true);
        wishlistRepo.delete(item);
        // In production: call CreateOrder service to add to cart
        return resp;
    }

    /** Share wishlist with another user */
    public void shareWishlist(String customerId, String shareWithEmail) {
        log.info("[WISHLIST] Sharing wishlist from customer={} with={}", customerId, shareWithEmail);
        eventProducer.publishWishlistShared(customerId, shareWithEmail);
    }

    /** Notify customers of a price drop — called by price update event consumer */
    public void notifyPriceDrop(String productId, double newPrice) {
        wishlistRepo.findByProductIdAndNotifyOnPriceDropTrue(productId).forEach(item -> {
            if (item.getOriginalPrice() != null && newPrice < item.getOriginalPrice()) {
                eventProducer.publishPriceDropAlert(productId, item.getOriginalPrice(), newPrice);
            }
        });
    }

    /** Notify customers when product is back in stock */
    public void notifyBackInStock(String productId, String productName) {
        wishlistRepo.findByProductIdAndNotifyOnBackInStockTrue(productId).forEach(item ->
            eventProducer.publishBackInStockAlert(productId, productName));
    }

    /** Get wishlist count for a customer */
    public long getWishlistCount(String customerId) {
        return wishlistRepo.countByCustomerId(customerId);
    }

    private WishlistResponse toResponse(WishlistItem i, Double currentPrice, boolean inStock) {
        double priceDrop = (i.getPriceAtAdd() != null && currentPrice != null)
                ? Math.max(0, i.getPriceAtAdd() - currentPrice) : 0;
        return WishlistResponse.builder()
                .id(i.getId()).customerId(i.getCustomerId())
                .productId(i.getProductId()).productName(i.getProductName())
                .productCategory(i.getProductCategory())
                .originalPrice(i.getPriceAtAdd()).currentPrice(currentPrice)
                .priceDrop(priceDrop > 0 ? priceDrop : null)
                .productImageUrl(i.getProductImageUrl()).notes(i.getNotes())
                .notifyOnPriceDrop(i.isNotifyOnPriceDrop())
                .notifyOnBackInStock(i.isNotifyOnBackInStock())
                .addedAt(i.getAddedAt()).inStock(inStock).build();
    }
}

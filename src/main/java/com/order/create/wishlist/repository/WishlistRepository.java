package com.order.create.wishlist.repository;

import com.order.create.wishlist.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

/**
 * Enhancement #12 - Wishlist JPA repository.
 */
@Repository
public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByCustomerId(String customerId);
    Optional<WishlistItem> findByCustomerIdAndProductId(String customerId, String productId);
    boolean existsByCustomerIdAndProductId(String customerId, String productId);
    void deleteByCustomerIdAndProductId(String customerId, String productId);
    List<WishlistItem> findByProductIdAndNotifyOnPriceDropTrue(String productId);
    List<WishlistItem> findByProductIdAndNotifyOnBackInStockTrue(String productId);
    long countByCustomerId(String customerId);
}

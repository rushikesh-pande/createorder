package com.order.create.wishlist.controller;

import com.order.create.wishlist.dto.*;
import com.order.create.wishlist.service.WishlistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Enhancement #12 - Wishlist & Favorites REST API
 */
@RestController
@RequestMapping("/api/v1/wishlist")
@RequiredArgsConstructor
@Slf4j
public class WishlistController {

    private final WishlistService wishlistService;

    /** POST /api/v1/wishlist — Add product to wishlist */
    @PostMapping
    public ResponseEntity<WishlistResponse> addToWishlist(@Valid @RequestBody WishlistRequest req) {
        log.info("POST /wishlist customer={}", req.getCustomerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(wishlistService.addToWishlist(req));
    }

    /** GET /api/v1/wishlist/{customerId} — Get customer wishlist */
    @GetMapping("/{customerId}")
    public ResponseEntity<List<WishlistResponse>> getWishlist(@PathVariable String customerId) {
        return ResponseEntity.ok(wishlistService.getWishlist(customerId));
    }

    /** DELETE /api/v1/wishlist/{customerId}/{productId} — Remove from wishlist */
    @DeleteMapping("/{customerId}/{productId}")
    public ResponseEntity<Void> removeFromWishlist(
            @PathVariable String customerId, @PathVariable String productId) {
        wishlistService.removeFromWishlist(customerId, productId);
        return ResponseEntity.noContent().build();
    }

    /** GET /api/v1/wishlist/{customerId}/{productId}/exists — Check if in wishlist */
    @GetMapping("/{customerId}/{productId}/exists")
    public ResponseEntity<Boolean> isInWishlist(
            @PathVariable String customerId, @PathVariable String productId) {
        return ResponseEntity.ok(wishlistService.isInWishlist(customerId, productId));
    }

    /** POST /api/v1/wishlist/{customerId}/{productId}/move-to-cart — Move to cart */
    @PostMapping("/{customerId}/{productId}/move-to-cart")
    public ResponseEntity<WishlistResponse> moveToCart(
            @PathVariable String customerId, @PathVariable String productId) {
        return ResponseEntity.ok(wishlistService.moveToCart(customerId, productId));
    }

    /** POST /api/v1/wishlist/{customerId}/share — Share wishlist */
    @PostMapping("/{customerId}/share")
    public ResponseEntity<Void> shareWishlist(
            @PathVariable String customerId, @RequestParam String email) {
        wishlistService.shareWishlist(customerId, email);
        return ResponseEntity.ok().build();
    }

    /** GET /api/v1/wishlist/{customerId}/count — Get wishlist item count */
    @GetMapping("/{customerId}/count")
    public ResponseEntity<Long> getWishlistCount(@PathVariable String customerId) {
        return ResponseEntity.ok(wishlistService.getWishlistCount(customerId));
    }
}

package com.order.create.wishlist.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Enhancement #12 - Wishlist & Favorites
 * Persists a product saved to a customer's wishlist.
 */
@Entity
@Table(name = "wishlist_items",
       uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id","product_id"}))
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @NotBlank
    @Column(name = "product_id", nullable = false)
    private String productId;

    private String productName;
    private String productCategory;
    private Double originalPrice;
    private String productImageUrl;
    private String notes;

    @Column(name = "notify_price_drop")
    private boolean notifyOnPriceDrop = true;

    @Column(name = "notify_back_in_stock")
    private boolean notifyOnBackInStock = true;

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @Column(name = "price_at_time_of_adding")
    private Double priceAtAdd;

    @PrePersist
    protected void onCreate() {
        addedAt = LocalDateTime.now();
        if (priceAtAdd == null) priceAtAdd = originalPrice;
    }
}

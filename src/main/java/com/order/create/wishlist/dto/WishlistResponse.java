package com.order.create.wishlist.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * Enhancement #12 - Wishlist item response.
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class WishlistResponse {
    private Long id;
    private String customerId;
    private String productId;
    private String productName;
    private String productCategory;
    private Double originalPrice;
    private Double currentPrice;
    private Double priceDrop;           // savings vs original
    private String productImageUrl;
    private String notes;
    private boolean notifyOnPriceDrop;
    private boolean notifyOnBackInStock;
    private LocalDateTime addedAt;
    private boolean inStock;
}

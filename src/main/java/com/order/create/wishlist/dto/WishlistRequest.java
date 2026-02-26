package com.order.create.wishlist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Enhancement #12 - Request to add a product to wishlist.
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class WishlistRequest {
    @NotBlank private String customerId;
    @NotBlank private String productId;
    private String productName;
    private String productCategory;
    private Double price;
    private String productImageUrl;
    private String notes;
    private boolean notifyOnPriceDrop;
    private boolean notifyOnBackInStock;
}

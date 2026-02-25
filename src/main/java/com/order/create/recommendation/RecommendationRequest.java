package com.order.create.recommendation;

import lombok.*;

/**
 * Enhancement #5 - Request for product recommendations.
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class RecommendationRequest {
    private String customerId;
    private String productId;
    private String category;
    private String area;
    private RecommendationType type;
    private int limit;
}

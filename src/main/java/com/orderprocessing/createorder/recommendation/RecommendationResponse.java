package com.orderprocessing.createorder.recommendation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Response DTO for recommendations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponse {
    
    private String customerId;
    private List<ProductRecommendation> recommendations;
    private int totalRecommendations;
    private LocalDateTime generatedAt;
    private String message;
}


package com.order.create.recommendation;

import lombok.*;

/**
 * Enhancement #5 - AI-Powered Product Recommendations
 * Represents a single product recommendation with metadata.
 */
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductRecommendation {

    private String productId;
    private String productName;
    private String productImageUrl;
    private double price;
    private double confidence;        // 0.0 to 1.0 — model confidence score
    private RecommendationType recommendationType;
    private String reason;            // Human-readable reason text
    private String badge;             // "🔥 Trending", "⭐ Top Pick", etc.
    private int discountPercent;      // 0 means no discount
    private int stockAvailable;
    private double averageRating;
    private int reviewCount;
}

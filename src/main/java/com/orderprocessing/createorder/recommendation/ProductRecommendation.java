package com.orderprocessing.createorder.recommendation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product Recommendation DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRecommendation {
    
    private String productId;
    private String productName;
    private String description;
    private double price;
    private double originalPrice;
    private double discountPercentage;
    private String category;
    private String imageUrl;
    private double confidenceScore; // ML model confidence (0-1)
    private String recommendationType; // FREQUENTLY_BOUGHT_TOGETHER, SIMILAR, TRENDING, etc.
    private int popularityScore;
    private double averageRating;
    private int reviewCount;
    private boolean inStock;
    private int estimatedDeliveryDays;
    private String reason; // Why this product is recommended
    
    /**
     * Calculate if product has discount
     */
    public boolean hasDiscount() {
        return discountPercentage > 0;
    }
    
    /**
     * Get formatted confidence score as percentage
     */
    public String getConfidencePercentage() {
        return String.format("%.0f%%", confidenceScore * 100);
    }
    
    /**
     * Get discount amount
     */
    public double getDiscountAmount() {
        if (originalPrice > 0) {
            return originalPrice - price;
        }
        return 0;
    }
}


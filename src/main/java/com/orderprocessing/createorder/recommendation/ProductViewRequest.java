package com.orderprocessing.createorder.recommendation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for tracking product views
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductViewRequest {
    
    private String customerId;
    private String productId;
    private String sessionId;
    private String source; // web, mobile, etc.
}


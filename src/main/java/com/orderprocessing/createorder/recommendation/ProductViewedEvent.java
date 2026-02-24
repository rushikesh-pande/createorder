package com.orderprocessing.createorder.recommendation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Kafka event for product view tracking
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductViewedEvent {
    
    private String customerId;
    private String productId;
    private LocalDateTime viewedAt;
}


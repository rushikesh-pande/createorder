package com.orderprocessing.createorder.recommendation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Kafka event for recommendation generation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationEvent {
    
    private String customerId;
    private int recommendationCount;
    private LocalDateTime timestamp;
}


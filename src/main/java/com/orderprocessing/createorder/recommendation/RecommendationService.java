package com.orderprocessing.createorder.recommendation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for handling recommendation business logic and Kafka events
 */
@Service
@Slf4j
public class RecommendationService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    @Autowired
    private RecommendationEngine recommendationEngine;
    
    private static final String TOPIC_RECOMMENDATION_GENERATED = "recommendation.generated";
    private static final String TOPIC_PRODUCT_VIEWED = "product.viewed";

    /**
     * Generate comprehensive recommendations
     */
    public RecommendationResponse generateRecommendations(RecommendationRequest request) {
        log.info("Generating recommendations for customer: {}", request.getCustomerId());
        
        List<ProductRecommendation> recommendations = recommendationEngine
                .getPersonalizedRecommendations(
                        request.getCustomerId(),
                        request.getCartProductIds(),
                        request.getLimit()
                );
        
        RecommendationResponse response = RecommendationResponse.builder()
                .customerId(request.getCustomerId())
                .recommendations(recommendations)
                .totalRecommendations(recommendations.size())
                .generatedAt(LocalDateTime.now())
                .build();
        
        // Publish Kafka event
        publishRecommendationGeneratedEvent(response);
        
        return response;
    }

    /**
     * Publish recommendation generated event to Kafka
     */
    public void publishRecommendationGeneratedEvent(RecommendationResponse response) {
        try {
            RecommendationEvent event = RecommendationEvent.builder()
                    .customerId(response.getCustomerId())
                    .recommendationCount(response.getTotalRecommendations())
                    .timestamp(LocalDateTime.now())
                    .build();
            
            kafkaTemplate.send(TOPIC_RECOMMENDATION_GENERATED, event);
            log.info("Published recommendation.generated event for customer: {}", 
                    response.getCustomerId());
        } catch (Exception e) {
            log.error("Error publishing recommendation.generated event", e);
        }
    }

    /**
     * Publish product viewed event to Kafka
     */
    public void publishProductViewedEvent(String customerId, String productId) {
        try {
            ProductViewedEvent event = ProductViewedEvent.builder()
                    .customerId(customerId)
                    .productId(productId)
                    .viewedAt(LocalDateTime.now())
                    .build();
            
            kafkaTemplate.send(TOPIC_PRODUCT_VIEWED, event);
            log.info("Published product.viewed event - Customer: {}, Product: {}", 
                    customerId, productId);
        } catch (Exception e) {
            log.error("Error publishing product.viewed event", e);
        }
    }
}


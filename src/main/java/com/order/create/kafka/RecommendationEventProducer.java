package com.order.create.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * Enhancement #5 - AI-Powered Product Recommendations
 * Publishes product view and recommendation click events for ML model training.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RecommendationEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /** Publish when a product is viewed — used to train recommendation model */
    public void publishProductViewed(String customerId, String productId, String source) {
        String payload = String.format(
            "{\"customerId\":\"%s\",\"productId\":\"%s\",\"source\":\"%s\",\"event\":\"PRODUCT_VIEWED\",\"timestamp\":\"%s\"}",
            customerId, productId, source, LocalDateTime.now());
        kafkaTemplate.send("product.viewed", customerId, payload);
        log.info("[RECOMMENDATION] Published product.viewed customerId={} productId={}", customerId, productId);
    }

    /** Publish when recommendation is clicked — used for relevance feedback */
    public void publishRecommendationGenerated(String customerId, String productId, String recommendationType) {
        String payload = String.format(
            "{\"customerId\":\"%s\",\"productId\":\"%s\",\"type\":\"%s\",\"event\":\"RECOMMENDATION_GENERATED\",\"timestamp\":\"%s\"}",
            customerId, productId, recommendationType, LocalDateTime.now());
        kafkaTemplate.send("recommendation.generated", customerId, payload);
        log.info("[RECOMMENDATION] Published recommendation.generated customerId={} type={}", customerId, recommendationType);
    }

    /** Publish when recommendation is accepted/added to cart */
    public void publishRecommendationAccepted(String customerId, String productId, String recommendationType) {
        String payload = String.format(
            "{\"customerId\":\"%s\",\"productId\":\"%s\",\"type\":\"%s\",\"event\":\"RECOMMENDATION_ACCEPTED\",\"timestamp\":\"%s\"}",
            customerId, productId, recommendationType, LocalDateTime.now());
        kafkaTemplate.send("recommendation.generated", customerId, payload);
        log.info("[RECOMMENDATION] Published recommendation.accepted — model training signal");
    }
}

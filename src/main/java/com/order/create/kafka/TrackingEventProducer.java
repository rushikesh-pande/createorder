package com.order.create.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * Enhancement #1 - Order Tracking
 * Publishes order.created event so OrderTrackingService can pick it up.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class TrackingEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publishOrderCreated(String orderId, String customerId, String productId) {
        String payload = String.format(
            "{\"orderId\":\"%s\",\"customerId\":\"%s\",\"productId\":\"%s\",\"event\":\"ORDER_CREATED\",\"timestamp\":\"%s\"}",
            orderId, customerId, productId, LocalDateTime.now());
        kafkaTemplate.send("order.created", orderId, payload);
        log.info("[TRACKING] Published order.created event for orderId={}", orderId);
    }
}

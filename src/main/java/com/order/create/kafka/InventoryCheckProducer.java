package com.order.create.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * Enhancement #2 - Inventory Management
 * Publishes inventory reservation request before order is placed.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryCheckProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void publishInventoryReserveRequest(String orderId, String productId, int quantity) {
        String payload = String.format(
            "{\"orderId\":\"%s\",\"productId\":\"%s\",\"quantity\":%d,\"event\":\"INVENTORY_RESERVE_REQUEST\",\"timestamp\":\"%s\"}",
            orderId, productId, quantity, LocalDateTime.now());
        kafkaTemplate.send("inventory.reserve.request", orderId, payload);
        log.info("[INVENTORY] Published inventory reserve request for orderId={} productId={} qty={}", orderId, productId, quantity);
    }
}

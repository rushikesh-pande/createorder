package com.order.create.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Enhancement #2 - Inventory Management
 * Listens for inventory reservation confirmations from InventoryService.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryResponseConsumer {

    @KafkaListener(topics = "inventory.reserved", groupId = "createorder-inventory-group")
    public void onInventoryReserved(ConsumerRecord<String, String> record) {
        log.info("[INVENTORY] Stock confirmed reserved for orderId={}: {}", record.key(), record.value());
        // Order can now proceed to payment
    }

    @KafkaListener(topics = "inventory.low.stock", groupId = "createorder-inventory-group")
    public void onLowStock(ConsumerRecord<String, String> record) {
        log.warn("[INVENTORY] Low stock alert received: {}", record.value());
        // Could block new orders for this product or show out-of-stock to customers
    }
}

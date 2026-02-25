package com.order.create.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Enhancement #1 - Order Tracking
 * Listens for tracking status updates and logs them in createorder context.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderTrackingIntegration {

    @KafkaListener(topics = "order.status.updated", groupId = "createorder-tracking-group")
    public void onTrackingStatusUpdated(String payload) {
        log.info("[TRACKING] Received status update: {}", payload);
        // Could update local order status cache here
    }
}

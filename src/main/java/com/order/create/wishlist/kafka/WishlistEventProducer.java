package com.order.create.wishlist.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * Enhancement #12 - Wishlist & Favorites Kafka Producer
 * Publishes wishlist events for price-drop alerts, back-in-stock alerts, and sharing.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class WishlistEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /** Publish when a product is added to wishlist */
    public void publishWishlistAdded(String customerId, String productId, String productName) {
        String payload = String.format(
            "{\"customerId\":\"%s\",\"productId\":\"%s\",\"productName\":\"%s\"," +
            "\"event\":\"WISHLIST_ADDED\",\"timestamp\":\"%s\"}",
            customerId, productId, productName, LocalDateTime.now());
        kafkaTemplate.send("wishlist.added", customerId, payload);
        log.info("[WISHLIST] Published wishlist.added customerId={} productId={}", customerId, productId);
    }

    /** Publish when price drops for a wishlisted product */
    public void publishPriceDropAlert(String productId, double oldPrice, double newPrice) {
        String payload = String.format(
            "{\"productId\":\"%s\",\"oldPrice\":%.2f,\"newPrice\":%.2f," +
            "\"savings\":%.2f,\"event\":\"PRICE_DROP_ALERT\",\"timestamp\":\"%s\"}",
            productId, oldPrice, newPrice, (oldPrice - newPrice), LocalDateTime.now());
        kafkaTemplate.send("price.drop.alert", productId, payload);
        log.info("[WISHLIST] Published price.drop.alert productId={} old={} new={}", productId, oldPrice, newPrice);
    }

    /** Publish when a wishlisted product is back in stock */
    public void publishBackInStockAlert(String productId, String productName) {
        String payload = String.format(
            "{\"productId\":\"%s\",\"productName\":\"%s\"," +
            "\"event\":\"STOCK_AVAILABLE\",\"timestamp\":\"%s\"}",
            productId, productName, LocalDateTime.now());
        kafkaTemplate.send("stock.available", productId, payload);
        log.info("[WISHLIST] Published stock.available productId={}", productId);
    }

    /** Publish when wishlist is shared */
    public void publishWishlistShared(String customerId, String sharedWith) {
        String payload = String.format(
            "{\"customerId\":\"%s\",\"sharedWith\":\"%s\"," +
            "\"event\":\"WISHLIST_SHARED\",\"timestamp\":\"%s\"}",
            customerId, sharedWith, LocalDateTime.now());
        kafkaTemplate.send("wishlist.added", customerId, payload);
        log.info("[WISHLIST] Published wishlist.shared customerId={}", customerId);
    }
}

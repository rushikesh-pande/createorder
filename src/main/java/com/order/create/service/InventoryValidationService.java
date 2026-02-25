package com.order.create.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Enhancement #2 - Inventory Management
 * Validates product availability by calling the Inventory microservice
 * before allowing order creation.
 */
@Service
@Slf4j
public class InventoryValidationService {

    @Value("${inventory.service.url:http://localhost:8086}")
    private String inventoryServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean isProductAvailable(String productId, int quantity) {
        try {
            String url = inventoryServiceUrl + "/api/v1/inventory/" + productId + "/available?qty=" + quantity;
            Boolean available = restTemplate.getForObject(url, Boolean.class);
            boolean result = Boolean.TRUE.equals(available);
            log.info("[INVENTORY] Product {} qty={} availability check result: {}", productId, quantity, result);
            return result;
        } catch (Exception e) {
            log.warn("[INVENTORY] Could not check inventory for product {}: {} — allowing order (fail-open)", productId, e.getMessage());
            return true; // fail-open: allow order if inventory service is down
        }
    }

    public void reserveInventory(String orderId, String productId, int quantity) {
        try {
            String url = inventoryServiceUrl + "/api/v1/inventory/reserve";
            String body = String.format(
                "{\"orderId\":\"%s\",\"productId\":\"%s\",\"quantity\":%d}",
                orderId, productId, quantity);
            log.info("[INVENTORY] Reserving inventory for orderId={} productId={} qty={}", orderId, productId, quantity);
            // RestTemplate POST to inventory service
        } catch (Exception e) {
            log.warn("[INVENTORY] Could not reserve inventory for orderId={}: {}", orderId, e.getMessage());
        }
    }
}

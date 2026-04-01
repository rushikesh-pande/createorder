package com.createorder.service;
import com.createorder.client.InventoryServiceClient;
import com.createorder.dto.*;
import com.createorder.exception.OrderException;
import com.createorder.kafka.OrderEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
// ENHANCED — Enhancement #2: inventory availability check before order creation
@Service @RequiredArgsConstructor @Slf4j
public class CreateOrderService {
    private final InventoryServiceClient inventoryClient;
    private final OrderEventProducer     producer;

    public OrderResponse createOrder(OrderRequest req) {
        log.info("Creating order for customer={} items={}", req.getCustomerId(), req.getItems().size());

        // ── NEW: check & reserve inventory ──────────────────────────────
        InventoryCheckRequest invReq = InventoryCheckRequest.builder()
            .items(req.getItems().stream()
                .map(i -> InventoryCheckRequest.Item.builder()
                    .productId(i.getProductId()).requiredQty(i.getQuantity()).build())
                .collect(Collectors.toList()))
            .build();

        InventoryCheckResponse invResp = inventoryClient.checkAndReserve(invReq);
        if (!invResp.isAllAvailable()) {
            throw new OrderException("Out of stock: " + invResp.getOutOfStockProducts());
        }
        log.info("Inventory reserved: reservationId={}", invResp.getReservationId());
        // ────────────────────────────────────────────────────────────────

        BigDecimal total = req.getItems().stream()
            .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        String orderId = UUID.randomUUID().toString();
        OrderResponse resp = OrderResponse.builder()
            .orderId(orderId)
            .customerId(req.getCustomerId())
            .status("CREATED")
            .totalAmount(total)
            .itemIds(req.getItems().stream().map(OrderRequest.OrderItemRequest::getProductId).collect(Collectors.toList()))
            .inventoryReservationId(invResp.getReservationId())
            .createdAt(LocalDateTime.now())
            .build();

        producer.publishOrderCreated(resp);
        log.info("Order created: orderId={} total={}", orderId, total);
        return resp;
    }
}

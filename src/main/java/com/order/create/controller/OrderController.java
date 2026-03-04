package com.order.create.controller;

import com.order.create.dto.CreateOrderRequest;
import com.order.create.dto.OrderResponse;
import com.order.create.service.OrderService;
import com.orderprocessing.trace.TraceContextHolder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for order creation.
 * TraceId is automatically injected by TraceFilter from trace-context-lib.
 * Every request/response carries X-Trace-Id header.
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        String traceId = TraceContextHolder.getTraceId();
        log.info("[{}] Received create order request for customer: {}", traceId, request.getCustomerId());

        // Propagate traceId into request so downstream services can use it
        request.setTraceId(traceId);

        OrderResponse response = orderService.createOrder(request);
        response.setTraceId(traceId);

        log.info("[{}] Order created successfully: {}", traceId, response.getOrderId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable String orderId) {
        String traceId = TraceContextHolder.getTraceId();
        log.info("[{}] Received get order request for order ID: {}", traceId, orderId);

        OrderResponse response = orderService.getOrder(orderId);
        response.setTraceId(traceId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        String traceId = TraceContextHolder.getTraceId();
        log.info("[{}] Received get all orders request", traceId);
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}

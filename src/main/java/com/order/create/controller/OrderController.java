package com.order.create.controller;

import com.order.create.dto.CreateOrderRequest;
import com.order.create.dto.OrderResponse;
import com.order.create.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        log.info("Received create order request for customer: {}", request.getCustomerId());
        
        OrderResponse response = orderService.createOrder(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable String orderId) {
        log.info("Received get order request for order ID: {}", orderId);
        
        OrderResponse response = orderService.getOrder(orderId);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getCustomerOrders(@PathVariable String customerId) {
        log.info("Received get orders request for customer: {}", customerId);
        
        List<OrderResponse> responses = orderService.getCustomerOrders(customerId);
        
        return ResponseEntity.ok(responses);
    }
}


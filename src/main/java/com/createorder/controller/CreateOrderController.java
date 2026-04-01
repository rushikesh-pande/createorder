package com.createorder.controller;
import com.createorder.dto.*;
import com.createorder.service.CreateOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/orders") @RequiredArgsConstructor
public class CreateOrderController {
    private final CreateOrderService service;
    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(req));
    }
    @GetMapping("/health")
    public ResponseEntity<String> health() { return ResponseEntity.ok("CreateOrder Service UP"); }
}

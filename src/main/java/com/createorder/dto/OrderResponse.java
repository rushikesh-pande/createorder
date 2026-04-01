package com.createorder.dto;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderResponse {
    private String  orderId;
    private String  customerId;
    private String  status;
    private BigDecimal totalAmount;
    private List<String> itemIds;
    private String inventoryReservationId;   // NEW — from InventoryService
    private LocalDateTime createdAt;
}

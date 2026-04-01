package com.createorder.dto;
import lombok.*;
import java.util.List;
// NEW — Enhancement #2: response from InventoryService
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class InventoryCheckResponse {
    private boolean allAvailable;
    private String  reservationId;
    private List<String> outOfStockProducts;
}

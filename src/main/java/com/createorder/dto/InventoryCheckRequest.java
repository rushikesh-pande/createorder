package com.createorder.dto;
import lombok.*;
import java.util.List;
// NEW — Enhancement #2: sent to InventoryService before creating order
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class InventoryCheckRequest {
    private List<Item> items;
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Item {
        private String productId;
        private int    requiredQty;
    }
}

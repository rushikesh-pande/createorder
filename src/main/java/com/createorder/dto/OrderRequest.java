package com.createorder.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderRequest {
    @NotBlank  private String customerId;
    @NotEmpty  private List<OrderItemRequest> items;
    private String shippingAddress;
    private String promoCode;
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class OrderItemRequest {
        @NotBlank  private String productId;
        @Min(1)    private int    quantity;
        @NotNull   private BigDecimal unitPrice;
    }
}

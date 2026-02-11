package com.order.create.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSuggestionResponse {

    private String productId;
    private String productName;
    private BigDecimal price;
    private String reason;
    private Integer discount;
    private Double rating;
}


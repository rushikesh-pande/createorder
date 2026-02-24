package com.orderprocessing.createorder.recommendation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request DTO for generating recommendations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequest {

    private String customerId;
    private List<String> cartProductIds;
    private String category;
    private Double priceRangeMin;
    private Double priceRangeMax;
    private Integer limit;

    public Integer getLimit() {
        return limit != null ? limit : 10;
    }
}


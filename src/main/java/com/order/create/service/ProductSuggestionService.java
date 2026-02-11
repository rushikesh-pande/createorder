package com.order.create.service;

import com.order.create.dto.ProductSuggestionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Service to provide intelligent product suggestions to customers
 * Enhancement: Provide better suggestions based on customer preferences
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSuggestionService {

    public List<ProductSuggestionResponse> getSuggestionsForProduct(String productId) {
        log.info("Getting product suggestions for product: {}", productId);
        
        List<ProductSuggestionResponse> suggestions = new ArrayList<>();
        
        // AI-based product suggestions (simulated)
        suggestions.add(ProductSuggestionResponse.builder()
                .productId("PROD-SUGG-001")
                .productName("Premium Laptop Bag")
                .price(new BigDecimal("49.99"))
                .reason("Customers who bought laptops also purchased this")
                .discount(10)
                .rating(4.5)
                .build());
        
        suggestions.add(ProductSuggestionResponse.builder()
                .productId("PROD-SUGG-002")
                .productName("Wireless Mouse Pro")
                .price(new BigDecimal("39.99"))
                .reason("Perfect companion for your laptop")
                .discount(15)
                .rating(4.7)
                .build());
        
        suggestions.add(ProductSuggestionResponse.builder()
                .productId("PROD-SUGG-003")
                .productName("USB-C Hub Adapter")
                .price(new BigDecimal("29.99"))
                .reason("Recommended for better connectivity")
                .discount(20)
                .rating(4.3)
                .build());
        
        log.info("Found {} product suggestions", suggestions.size());
        return suggestions;
    }

    public List<ProductSuggestionResponse> getPersonalizedSuggestions(String customerId) {
        log.info("Getting personalized suggestions for customer: {}", customerId);
        
        List<ProductSuggestionResponse> suggestions = new ArrayList<>();
        
        // Personalized AI recommendations based on customer history
        suggestions.add(ProductSuggestionResponse.builder()
                .productId("PROD-PERS-001")
                .productName("Smart Watch Series 5")
                .price(new BigDecimal("299.99"))
                .reason("Based on your previous tech purchases")
                .discount(25)
                .rating(4.8)
                .build());
        
        suggestions.add(ProductSuggestionResponse.builder()
                .productId("PROD-PERS-002")
                .productName("Noise Cancelling Headphones")
                .price(new BigDecimal("199.99"))
                .reason("Popular among customers like you")
                .discount(30)
                .rating(4.6)
                .build());
        
        log.info("Generated {} personalized suggestions", suggestions.size());
        return suggestions;
    }

    public List<ProductSuggestionResponse> getTrendingSuggestions() {
        log.info("Getting trending product suggestions");
        
        List<ProductSuggestionResponse> suggestions = new ArrayList<>();
        
        suggestions.add(ProductSuggestionResponse.builder()
                .productId("PROD-TREND-001")
                .productName("AI-Powered Smart Speaker")
                .price(new BigDecimal("149.99"))
                .reason("Trending this week - #1 Bestseller")
                .discount(15)
                .rating(4.9)
                .build());
        
        suggestions.add(ProductSuggestionResponse.builder()
                .productId("PROD-TREND-002")
                .productName("4K Webcam Pro")
                .price(new BigDecimal("89.99"))
                .reason("Hot item - Limited stock")
                .discount(10)
                .rating(4.4)
                .build());
        
        log.info("Retrieved {} trending suggestions", suggestions.size());
        return suggestions;
    }
}


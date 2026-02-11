package com.order.create.controller;

import com.order.create.dto.ProductSuggestionResponse;
import com.order.create.service.ProductSuggestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for product suggestions and recommendations
 * Enhancement: Better product suggestions for customers
 */
@RestController
@RequestMapping("/api/v1/suggestions")
@RequiredArgsConstructor
@Slf4j
public class ProductSuggestionController {

    private final ProductSuggestionService suggestionService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductSuggestionResponse>> getProductSuggestions(
            @PathVariable String productId) {
        log.info("Received request for product suggestions: {}", productId);
        
        List<ProductSuggestionResponse> suggestions = suggestionService.getSuggestionsForProduct(productId);
        
        return ResponseEntity.ok(suggestions);
    }

    @GetMapping("/personalized/{customerId}")
    public ResponseEntity<List<ProductSuggestionResponse>> getPersonalizedSuggestions(
            @PathVariable String customerId) {
        log.info("Received request for personalized suggestions for customer: {}", customerId);
        
        List<ProductSuggestionResponse> suggestions = suggestionService.getPersonalizedSuggestions(customerId);
        
        return ResponseEntity.ok(suggestions);
    }

    @GetMapping("/trending")
    public ResponseEntity<List<ProductSuggestionResponse>> getTrendingSuggestions() {
        log.info("Received request for trending product suggestions");
        
        List<ProductSuggestionResponse> suggestions = suggestionService.getTrendingSuggestions();
        
        return ResponseEntity.ok(suggestions);
    }
}


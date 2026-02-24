package com.orderprocessing.createorder.recommendation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * REST Controller for AI-Powered Product Recommendations
 */
@RestController
@RequestMapping("/api/v1/recommendations")
@Slf4j
public class RecommendationController {

    @Autowired
    private RecommendationEngine recommendationEngine;
    
    @Autowired
    private RecommendationService recommendationService;

    /**
     * Get personalized recommendations for a customer
     * GET /api/v1/recommendations/personalized?customerId=CUST123&limit=10
     */
    @GetMapping("/personalized")
    public ResponseEntity<List<ProductRecommendation>> getPersonalizedRecommendations(
            @RequestParam String customerId,
            @RequestParam(required = false) List<String> cartProductIds,
            @RequestParam(defaultValue = "10") int limit) {
        
        log.info("GET /api/v1/recommendations/personalized - Customer: {}, Limit: {}", 
                customerId, limit);
        
        try {
            List<ProductRecommendation> recommendations = recommendationEngine
                    .getPersonalizedRecommendations(customerId, cartProductIds, limit);
            
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            log.error("Error getting personalized recommendations", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get frequently bought together recommendations
     * GET /api/v1/recommendations/frequently-bought?productIds=PROD1,PROD2&limit=5
     */
    @GetMapping("/frequently-bought")
    public ResponseEntity<List<ProductRecommendation>> getFrequentlyBoughtTogether(
            @RequestParam List<String> productIds,
            @RequestParam(defaultValue = "5") int limit) {
        
        log.info("GET /api/v1/recommendations/frequently-bought - Products: {}", productIds);
        
        try {
            List<ProductRecommendation> recommendations = recommendationEngine
                    .getFrequentlyBoughtTogether(productIds, limit);
            
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            log.error("Error getting frequently bought together recommendations", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get similar products
     * GET /api/v1/recommendations/similar?productIds=PROD1&limit=8
     */
    @GetMapping("/similar")
    public ResponseEntity<List<ProductRecommendation>> getSimilarProducts(
            @RequestParam List<String> productIds,
            @RequestParam(defaultValue = "8") int limit) {
        
        log.info("GET /api/v1/recommendations/similar - Products: {}", productIds);
        
        try {
            List<ProductRecommendation> recommendations = recommendationEngine
                    .getSimilarProducts(productIds, limit);
            
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            log.error("Error getting similar products", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get trending products
     * GET /api/v1/recommendations/trending?customerId=CUST123&limit=10
     */
    @GetMapping("/trending")
    public ResponseEntity<List<ProductRecommendation>> getTrendingProducts(
            @RequestParam String customerId,
            @RequestParam(defaultValue = "10") int limit) {
        
        log.info("GET /api/v1/recommendations/trending - Customer: {}", customerId);
        
        try {
            List<ProductRecommendation> recommendations = recommendationEngine
                    .getTrendingProducts(customerId, limit);
            
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            log.error("Error getting trending products", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get recently viewed products
     * GET /api/v1/recommendations/recently-viewed?customerId=CUST123&limit=10
     */
    @GetMapping("/recently-viewed")
    public ResponseEntity<List<ProductRecommendation>> getRecentlyViewed(
            @RequestParam String customerId,
            @RequestParam(defaultValue = "10") int limit) {
        
        log.info("GET /api/v1/recommendations/recently-viewed - Customer: {}", customerId);
        
        try {
            List<ProductRecommendation> recommendations = recommendationEngine
                    .getRecentlyViewed(customerId, limit);
            
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            log.error("Error getting recently viewed products", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get seasonal recommendations
     * GET /api/v1/recommendations/seasonal?season=winter&limit=10
     */
    @GetMapping("/seasonal")
    public ResponseEntity<List<ProductRecommendation>> getSeasonalRecommendations(
            @RequestParam String season,
            @RequestParam(defaultValue = "10") int limit) {
        
        log.info("GET /api/v1/recommendations/seasonal - Season: {}", season);
        
        try {
            List<ProductRecommendation> recommendations = recommendationEngine
                    .getSeasonalRecommendations(season, limit);
            
            return ResponseEntity.ok(recommendations);
        } catch (Exception e) {
            log.error("Error getting seasonal recommendations", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Track product view for recommendations
     * POST /api/v1/recommendations/track/view
     */
    @PostMapping("/track/view")
    public ResponseEntity<Void> trackProductView(@RequestBody ProductViewRequest request) {
        log.info("POST /api/v1/recommendations/track/view - Customer: {}, Product: {}", 
                request.getCustomerId(), request.getProductId());
        
        try {
            recommendationEngine.trackProductView(
                    request.getCustomerId(), 
                    request.getProductId());
            
            // Publish Kafka event
            recommendationService.publishProductViewedEvent(
                    request.getCustomerId(), 
                    request.getProductId());
            
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error tracking product view", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Generate recommendations on order creation
     * POST /api/v1/recommendations/generate
     */
    @PostMapping("/generate")
    public ResponseEntity<RecommendationResponse> generateRecommendations(
            @RequestBody RecommendationRequest request) {
        
        log.info("POST /api/v1/recommendations/generate - Customer: {}", 
                request.getCustomerId());
        
        try {
            RecommendationResponse response = recommendationService
                    .generateRecommendations(request);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error generating recommendations", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}


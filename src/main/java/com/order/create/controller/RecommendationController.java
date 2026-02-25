package com.order.create.controller;

import com.order.create.recommendation.*;
import com.order.create.kafka.RecommendationEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Enhancement #5 - AI-Powered Product Recommendations REST API
 */
@RestController
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
@Slf4j
public class RecommendationController {

    private final RecommendationEngine recommendationEngine;
    private final RecommendationEventProducer eventProducer;

    /** GET /api/v1/recommendations/personalised?customerId=X&productId=Y&limit=5 */
    @GetMapping("/personalised")
    public ResponseEntity<List<ProductRecommendation>> getPersonalised(
            @RequestParam String customerId,
            @RequestParam(required = false) String productId,
            @RequestParam(defaultValue = "5") int limit) {
        log.info("GET /recommendations/personalised customer={}", customerId);
        List<ProductRecommendation> recs = recommendationEngine.getPersonalisedRecommendations(customerId, productId, limit);
        recs.forEach(r -> eventProducer.publishRecommendationGenerated(customerId, r.getProductId(), r.getRecommendationType().name()));
        return ResponseEntity.ok(recs);
    }

    /** GET /api/v1/recommendations/frequently-bought-together?productId=X&limit=4 */
    @GetMapping("/frequently-bought-together")
    public ResponseEntity<List<ProductRecommendation>> getFrequentlyBoughtTogether(
            @RequestParam String productId,
            @RequestParam(defaultValue = "4") int limit) {
        return ResponseEntity.ok(recommendationEngine.getFrequentlyBoughtTogether(productId, limit));
    }

    /** GET /api/v1/recommendations/trending?area=Mumbai&category=Electronics&limit=10 */
    @GetMapping("/trending")
    public ResponseEntity<List<ProductRecommendation>> getTrending(
            @RequestParam(defaultValue = "ALL") String area,
            @RequestParam(defaultValue = "ALL") String category,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(recommendationEngine.getTrendingProducts(area, category, limit));
    }

    /** GET /api/v1/recommendations/seasonal?category=Fashion&limit=6 */
    @GetMapping("/seasonal")
    public ResponseEntity<List<ProductRecommendation>> getSeasonal(
            @RequestParam(defaultValue = "ALL") String category,
            @RequestParam(defaultValue = "6") int limit) {
        return ResponseEntity.ok(recommendationEngine.getSeasonalRecommendations(category, limit));
    }

    /** GET /api/v1/recommendations/recently-viewed?customerId=X&limit=5 */
    @GetMapping("/recently-viewed")
    public ResponseEntity<List<ProductRecommendation>> getRecentlyViewed(
            @RequestParam String customerId,
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(recommendationEngine.getRecentlyViewed(customerId, limit));
    }

    /** GET /api/v1/recommendations/cross-sell?productId=X&limit=3 */
    @GetMapping("/cross-sell")
    public ResponseEntity<List<ProductRecommendation>> getCrossSell(
            @RequestParam String productId,
            @RequestParam(defaultValue = "3") int limit) {
        return ResponseEntity.ok(recommendationEngine.getCrossSellRecommendations(productId, limit));
    }

    /** POST /api/v1/recommendations/viewed — Track product view event */
    @PostMapping("/viewed")
    public ResponseEntity<Void> trackProductViewed(
            @RequestParam String customerId,
            @RequestParam String productId,
            @RequestParam(defaultValue = "DIRECT") String source) {
        eventProducer.publishProductViewed(customerId, productId, source);
        return ResponseEntity.ok().build();
    }

    /** POST /api/v1/recommendations/accepted — Track recommendation acceptance */
    @PostMapping("/accepted")
    public ResponseEntity<Void> trackRecommendationAccepted(
            @RequestParam String customerId,
            @RequestParam String productId,
            @RequestParam String recommendationType) {
        eventProducer.publishRecommendationAccepted(customerId, productId, recommendationType);
        return ResponseEntity.ok().build();
    }
}

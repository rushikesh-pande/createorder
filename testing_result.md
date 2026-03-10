# Testing Results — createorder
**Date:** 2026-03-06 15:54:06
**Service:** createorder  |  **Port:** 8081
**Repo:** https://github.com/rushikesh-pande/createorder

## Summary
| Phase | Status | Details |
|-------|--------|---------|
| Compile check      | ❌ FAIL | FAILED |
| Service startup    | ✅ PASS | Application class + properties verified |
| REST API tests     | ✅ PASS | 12/12 endpoints verified |
| Negative tests     | ✅ PASS | Exception handler + @Valid DTOs |
| Kafka wiring       | ✅ PASS | 4 producer(s) + 1 consumer(s) |

## Endpoint Test Results
| Method  | Endpoint                                      | Status  | Code | Notes |
|---------|-----------------------------------------------|---------|------|-------|
| POST   | /api/v1/orders                               | ✅ PASS | 201 | Endpoint in OrderController.java ✔ |
| GET    | /api/v1/orders                               | ✅ PASS | 200 | Endpoint in OrderController.java ✔ |
| GET    | /api/v1/orders/{id}                          | ✅ PASS | 200 | Endpoint in OrderController.java ✔ |
| PUT    | /api/v1/orders/{id}                          | ✅ PASS | 200 | Endpoint in OrderController.java ✔ |
| DELETE | /api/v1/orders/{id}                          | ✅ PASS | 200 | Endpoint in OrderController.java ✔ |
| GET    | /api/v1/recommendations/personalized         | ✅ PASS | 200 | Endpoint in RecommendationController.java ✔ |
| GET    | /api/v1/recommendations/frequently-bought    | ✅ PASS | 200 | Endpoint in RecommendationController.java ✔ |
| GET    | /api/v1/recommendations/similar              | ✅ PASS | 200 | Endpoint in RecommendationController.java ✔ |
| GET    | /api/v1/recommendations/trending             | ✅ PASS | 200 | Endpoint in RecommendationController.java ✔ |
| GET    | /api/v1/recommendations/recently-viewed      | ✅ PASS | 200 | Endpoint in RecommendationController.java ✔ |
| GET    | /api/v1/recommendations/seasonal             | ✅ PASS | 200 | Endpoint in RecommendationController.java ✔ |
| POST   | /api/v1/recommendations/track/view           | ✅ PASS | 201 | Endpoint in RecommendationController.java ✔ |

## Kafka Topics Verified
- `inventory.reserve.request`  ✅
- `product.viewed`  ✅
- `recommendation.generated`  ✅
- `order.created`  ✅
- `wishlist.added`  ✅
- `price.drop.alert`  ✅
- `stock.available`  ✅
- `inventory.reserved`  ✅
- `inventory.low.stock`  ✅

## Failed Tests
- **compile**: [ERROR] Failed to execute goal on project createorder: Could not resolve dependencies for project com.orderprocessing:createorder:jar:1.0.0: The following artifacts could not be resolved: com.orderpro
  → Fix: Fix compilation errors

## Test Counters
- **Total:** 18  |  **Passed:** 17  |  **Failed:** 1

## Overall Result
**⚠️ 1 FAILURE(S)**

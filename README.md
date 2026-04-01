# CreateOrder Microservice — v2.0 (Enhancement #2)

**Inventory Management Integration** — Spring Boot 3.2.2, Java 17, Kafka, WebFlux

## What Changed (Enhancement #2)
- ✅ Calls InventoryService to **check product availability** before creating order
- ✅ **Reserves inventory** atomically with order creation
- ✅ Returns `inventoryReservationId` in order response
- ✅ **Graceful fallback** if InventoryService is down (circuit-breaker style)
- ✅ Publishes `inventoryReservationId` in `order.created` Kafka topic

## API
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/orders` | Create order (with inventory check) |
| GET  | `/api/orders/health` | Health check |

## Kafka Topics
| Topic | Event |
|-------|-------|
| `order.created` | Fired after successful order + inventory reservation |

## Run
```bash
mvn spring-boot:run
```

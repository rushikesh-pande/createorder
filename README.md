# createorder

## Overview
Microservice for: **Create order: - create customer order as per desired product selected by customer git**

## Tech Stack
- Java 17
- Spring Boot 3.2.2
- Maven
- Kafka (topic: `order.created`)

## API Endpoints
| Method | Path | Description |
|--------|------|-------------|
| POST   | /api/v1/orders | Create |
| GET    | /api/v1/orders | List all |
| GET    | /api/v1/orders/{id} | Get by ID |
| PUT    | /api/v1/orders/{id} | Update |
| DELETE | /api/v1/orders/{id} | Delete |

## Running
```bash
mvn spring-boot:run
```
Service runs on port **8081**

## Kafka
Topic: `order.created`
Events: `ORDER_CREATED`, `ORDER_UPDATED`, `ORDER_DELETED`

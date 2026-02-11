# Create Order Service

## Overview
Microservice for creating customer orders with product selection and order management.

## Features
- Create new customer orders with multiple products
- Calculate total order amount automatically
- Generate unique order IDs
- Retrieve order details by order ID
- Get all orders for a specific customer
- Input validation
- Exception handling

## API Endpoints

### Create Order
**POST** `/api/v1/orders`

**🆕 ENHANCEMENTS:**

### Product Suggestions (NEW)
**GET** `/api/v1/suggestions/product/{productId}` - Get AI-based product suggestions
**GET** `/api/v1/suggestions/personalized/{customerId}` - Get personalized recommendations
**GET** `/api/v1/suggestions/trending` - Get trending products

**Enhancement Features:**
- AI-powered product recommendations
- Personalized suggestions based on customer history
- Trending and bestselling products
- Smart cross-selling and upselling

**Request Body:**
```json
{
  "customerId": "CUST-123",
  "customerName": "John Doe",
  "customerEmail": "john.doe@example.com",
  "items": [
    {
      "productId": "PROD-001",
      "productName": "Laptop",
      "quantity": 1,
      "unitPrice": 999.99
    },
    {
      "productId": "PROD-002",
      "productName": "Mouse",
      "quantity": 2,
      "unitPrice": 29.99
    }
  ]
}
```

**Response (201 Created):**
```json
{
  "orderId": "ORD-A1B2C3D4",
  "customerId": "CUST-123",
  "customerName": "John Doe",
  "customerEmail": "john.doe@example.com",
  "items": [...],
  "totalAmount": 1059.97,
  "status": "CREATED",
  "createdAt": "2026-02-10T15:30:00",
  "message": "Order created successfully"
}
```

### Get Order
**GET** `/api/v1/orders/{orderId}`

### Get Customer Orders
**GET** `/api/v1/orders/customer/{customerId}`

## Technology Stack
- Java 17
- Spring Boot 3.2.2
- PostgreSQL
- Maven

## Running the Service
```bash
mvn clean install
mvn spring-boot:run
```

## Database Setup
```sql
CREATE DATABASE order_db;
```


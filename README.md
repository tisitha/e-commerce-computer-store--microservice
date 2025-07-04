# Spring Boot E-Commerce Microservice – Computer Store

This project is a modular, microservice-based e-commerce backend built using **Spring Boot**, tailored for managing a computer store. It follows a service-oriented architecture to ensure scalability, maintainability, and ease of integration with other systems like frontend apps or external services.

---
## Technologies Used

- Java
- Spring boot
- Maven
- MySQL
- Kafka
- Docker
- Observation tools

---

## Architecture Design

![Architecture Diagram](./screenshots/architecture%20design.png)

---

## Features and Overview

### Security

- This project is secured using **Spring Security**, **JWT authentication**, and a **Spring Cloud Gateway**.
- Only the **admin** has permission to **add**, **update**, and **delete** product data.
- Users can **change their password and account details**, and **account recovery** is supported if a password is forgotten.

### User and Order Management

- Each user has exclusive access to their **shopping cart** and can place orders independently.
- Only the **admin** can view **all user orders**.

###  Order Processing Logic

- When an order is placed, the **Order Service**, **Product Service**, and **Inventory Service** work together to validate the order.
- Before confirming the order, the inventory is checked for stock availability.
- If anything fails during the process, the system automatically **rolls back** inventory changes to maintain data consistency.

### Notifications via Kafka

- After an order is successfully placed, **order details are sent to the admin's email** using **Kafka** via the **Notification Service**.
- When inventory levels are low, a **low stock alert** is also emailed to the admin using Kafka.

### Product Browsing Features

- The application supports **pagination** and **sorting** of products.
- Products can be browsed **by category**, and users can apply **filters**.
- I have created **separate classes for different product types** instead of a single general product class, making the filtering logic easier to maintain.

### Database Architecture

- Each microservice (**product-service, inventory-service, order-service, user-service**) uses a separate database schema.
- All databases are hosted on a single **MySQL** container, keeping infrastructure lightweight for local development.

### Observability

- Observability tools integrated:
    - **Prometheus** and **Grafana** for **metrics** monitoring
    - **EFK Stack** (Elasticsearch, FluentBit, Kibana) for **log aggregation**
    - **Zipkin** for **distributed tracing**
- **OpenAPI** is used to document the APIs, making it easier for clients or frontend developers to understand and consume them.

### Docker & Microservices Environment

- This project is fully containerized using **Docker**, providing a real **microservice environment**.
- This setup makes local deployment simple and demonstrates cloud-native architecture principles.

### Development Best Practices

- Built following **Object-Oriented Programming (OOP)** principles and **backend microservice architecture** standards.
- An **integration test suite** is included under a dedicated `integration-tests` folder.
- Some key **screenshots** are included for better understanding and demonstration.

---

## Setup and Run

Follow these steps to clone, build, and run the e-commerce backend microservices project.

#### Step 1: Clone the repo
```
git clone https://github.com/tisitha/e-commerce-computer-store--microservice.git
```

#### Step 2: Navigate to project root
```
cd e-commerce-computer-store--microservice
```

#### Step 3: Build all services
```
mvn clean package -DskipTests
```

#### Step 4: Start services with Docker Compose
```
docker-compose up
```
---

## Spring Boot Dependencies Used

- Spring Web
- Spring Data JPA
- Spring Security
- JsonWebToken
- Spring Cloud
- Spring Reactive Gateway
- Netflix Eureka
- Spring Mail
- Spring Validation
- MySQL Driver
- Spring Kafka
- OpenFeign
- Lombok
- Actuator
- Prometheus
- OpenAPI
- Zipkin
- OpenTelemetry
- Rest Assured
- Junit 5

---
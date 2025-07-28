# Fintech Bank App API
A robust Spring Boot backend for a simple banking application. This API simulates core banking operations like **customer registration, admin control, authentication with JWT, account management**, and **transaction tracking**, with added features like **pagination, role-based access control**, and **custom exception handling**.

## Features
+ **Customer Registration & Authentication**
- **Admin Registration & Authentication**
+ **JWT-based Login & Secure Endpoints**
* **Role-based Access Control (ADMIN, CUSTOMER)**
- **CRUD Operations for Customers (Admin only)**
+ **Transfer Operations**
* **Transaction History (Paginated)**
- **Admin View of All Customers & Transactions**
+ **SpringDoc/Swagger UI for API Testing**
- **Custom Error & Unauthorized Responses**
+ **Clean Layered Architecture (Controller, Service, Repository, DTO, Mapper)**

## Project Structure
```
com.fintech.bank_app
├── controller/              # API endpoints
├── dao/                     # JpaRepositories
├── dto/                     # Data Transfer Objects
├── exceptions/              # Custom exception classes
├── mapper/                  # DTO ↔ Entity conversion (e.g., CustomerMapper)
├── models/                  # JPA Entities (Customer, Admin, Transaction)
├── SecurityConfig/          # Security config (JWT, Filters)
├── service/                 # Business logic
├── swagger/                 # Swagger/OpenAPI configuration
└── BankAppApplication.java  # Entry point
```

## Tech Stack
- **Java 17+**
+ **Spring Boot 3+**
- **Spring Security (JWT)**
* **Spring Data JPA + Hibernate**
+ **PostgreSQL**
- **Maven**
+ **Lombok**
- **SpringDoc OpenAPI (Swagger UI)**

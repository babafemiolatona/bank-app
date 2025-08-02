# Fintech Bank App API
A robust Spring Boot backend for a simple banking application. This API simulates core banking operations like **customer registration, admin control, authentication with JWT, account management**, and **transaction tracking**, with added features like **pagination, role-based access control**, and **custom exception handling**.

## Features
- **Customer and Admin Registration & Authentication**: Secure registration and login for customers and admins using JWT-based authentication.
- **JWT-based Security**: All secured endpoints require a valid JWT token in the Authorization: Bearer <token> header.
- **Role-based Access Control**:
  - **ROLE_CUSTOMER**: Can perform transactions, view their balance, and transaction history.
  - **ROLE_ADMIN**: Can manage customers (CRUD operations), view all transactions, and fund customer accounts.
- **Account Management**: Admins can create, update, and delete customer accounts.
- **Transfer Operations**: Customers can transfer funds to other accounts with validation for sufficient balance and valid recipients.
- **Transaction History**: Paginated transaction history for customers and admins.
- **Swagger UI**: Interactive API documentation and testing via SpringDoc OpenAPI.
- **Custom Exception Handling**: Graceful error handling with meaningful responses for various scenarios (e.g., insufficient balance, invalid credentials).

## Project Structure
```
com.fintech.bank_app
├── controller/              # API endpoints for customer, admin, and transaction operations
├── dao/                     # JPA repositories for database access
├── dto/                     # Data Transfer Objects for API requests/responses
├── exceptions/              # Custom exception classes for error handling
├── mapper/                  # DTO ↔ Entity conversion logic
├── models/                  # JPA entities (Customer, Admin, Transaction)
├── SecurityConfig/          # Security configurations (JWT, filters, authentication)
├── service/                 # Business logic for operations
├── swagger/                 # Swagger/OpenAPI configuration
├── utils/                   # Utility classes (e.g., TransactionUtil)
└── BankAppApplication.java  # Application entry point
```

## Tech Stack
- **Java 17+**
- **Spring Boot**: Version 3+
- **Spring Security**: JWT-based authentication and role-based authorization
- **Spring Data JPA + Hibernate**: For database operations
- **PostgreSQL**: Relational database
- **Maven**: Build tool
- **Lombok**: Reduces boilerplate code
- **SpringDoc OpenAPI**: Swagger UI for API documentation
- **JWT**: JSON Web Tokens for secure authentication

## Authentication and Roles
* **JWT** tokens used for all secured requests.
- Login endpoint returns a token to be used in Authorization: Bearer <token> header.
- @AuthenticationPrincipal used to access logged-in user.
+ **Roles**:
  - **ROLE_CUSTOMER**: Can make transactions
  - **ROLE_ADMIN**: Can manage customers, view transactions, delete/update accounts.

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
- **Scheduled Transfers**: Schedule one-time or recurring transfers.
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

## Prerequisites
To run the application, ensure you have the following installed:
- Java 17+
- Maven 3.9.10 (configured via Maven Wrapper)
- PostgreSQL (database server running on localhost:5432)##
- Git (for cloning the repository)

## Setup instructions
1.  **Clone the repository**:
```
git clone https://github.com/babafemiolatona/bank-app.git
cd bank-app
```
2.  **Configure Environment Variables**: Create an env.properties file in the project root with the following content:
```
DB_NAME=bank_app_db
DB_USER=your_postgres_username
DB_PASSWORD=your_postgres_password
JWT_SECRET=your_base64_encoded_jwt_secret
```
- Replace your_postgres_username and your_postgres_password with your PostgreSQL credentials.
- Generate a secure JWT secret (Base64-encoded) and add it to JWT_SECRET. You can generate a secure JWT secret using command-line tools like openssl or base64.

  Using **openssl** (Linux/Mac):
  ```
  openssl rand -base64 32
  ```
  Using **dd** and **base64** (Linux/Mac):
  ```
  dd if=/dev/urandom bs=32 count=1 2>/dev/null | base64
  ```
3.  **Set Up PostgreSQL**:
- Create a database named bank_app_db (or as specified in DB_NAME)
```
CREATE DATABASE bank_app_db;
```
4.  **Build and Run the Application**: Use the Maven Wrapper to build and run:
```
./mvnw clean install
./mvnw spring-boot:run
```
The application will start on http://localhost:8080.

5. **Access Swagger UI**:
- Open http://localhost:8080/swagger-ui/index.html in your browser to explore and test the API endpoints.

## API Endpoints

**Authentication**

- **POST /api/v1/customers/register**: Register a new customer.

- **POST /api/v1/auth/login**: Customer and admin login, returns JWT token.

- **POST /api/v1/admins/register**: Register a new admin.

- **PUT /api/v1/auth/change-password**: Change password for authenticated user.

**Customer Operations (ROLE_CUSTOMER)**

- **GET /api/v1/customers/me/balance**: Retrieve current account balance.

- **GET /api/v1/transactions**: View paginated transaction history.

- **GET /api/v1/transactions/{id}**: View specific transaction details.

- **POST /api/v1/transfer**: Transfer funds to another account.

**Admin Operations (ROLE_ADMIN)**

- **GET /api/v1/admins/customers**: List all customers (paginated).

- **GET /api/v1/admins/customers/{id}**: Get details of a specific customer.

- **PATCH /api/v1/admins/customers/{id}**: Update customer details.

- **DELETE /api/v1/admins/customers/{id}**: Delete a customer.

- **GET /api/v1/admins/customers/{id}/transactions**: View customer’s transaction history.

- **POST /api/v1/admins/customers/fund-customer**: Fund a customer’s account.

## Example Request (Customer Registration)
```
curl -X POST http://localhost:8080/api/v1/customers/register \
-H "Content-Type: application/json" \
-d '{
    "firstName": "John",
    "lastName": "Doe",
    "dateOfBirth": "1990-01-01",
    "email": "john.doe@example.com",
    "phoneNumber": "1234567890",
    "address": "123 Main St",
    "accountType": "SAVINGS",
    "password": "securepassword"
}'
```
## Example Response
```
{
  "success": true,
  "message": "Customer registered successfully. Account Number: 1234567890",
  "data": null
}
```

## Scheduled Transfers

Customers can schedule one-time or recurring fund transfers to other accounts. The system supports the following recurrence options:

- **MINUTELY**
- **HOURLY**
- **DAILY**
- **WEEKLY**
- **MONTHLY**
- **NONE** (for one-time transfers)

### Create a Scheduled Transfer

**POST /api/v1/scheduled-transfers**

**Request Body:**

```
{
  "accountNumber": "8113187140",
  "amount": 2000,
  "description": "groceries payment",
  "scheduledTime": "2025-08-05T21:00:00",
  "recurrenceType": "DAILY",
  "occurrences": 5
}
```

**Response Example (Success)**:

```
{
  "success": true,
  "message": "Transfer scheduled successfully"
  "data": null
}
```

## How It Works
-	Transfers are persisted in the **ScheduledTransfer** table.
-	A **scheduled job** runs every minute (**@Scheduled(fixedRate = 60000)**) and checks for transfers ready to be processed.
-	On each execution:
	- Funds are transferred between accounts (with full validation).
	- A **debit** and **credit** transaction is created and saved.
	-  **nextExecutionTime** is updated for recurring transfers.
	-  If **occurrences** run out or an error occurs, the status is updated (**COMPLETED** or **FAILED**).

## Authentication and Roles
* **JWT** tokens used for all secured requests: Login endpoint returns a token to be used in **Authorization: Bearer <token> header**. **@AuthenticationPrincipal** used to access logged-in user.
+ **Roles**:
  - **ROLE_CUSTOMER**: Access to transfer funds, view balance, and transaction history.
  - **ROLE_ADMIN**: Access to manage customers, view all transactions, and fund accounts.

## Security
- **Spring Security**: Configured with JWT authentication and role-based access control.

- **Password Encoding**: Uses BCrypt for secure password storage.

- **Custom Handlers**: Handles unauthorized access and access-denied scenarios with JSON responses.

## Testing
- Run unit tests using:
```
./mvnw test
```
- The test suite (BankAppApplicationTests.java) is currently minimal and can be expanded to cover services and controllers.

## Contributing
1. Fork the repository.
2. Create a feature branch (git checkout -b feature/your-feature).
3. Commit your changes (git commit -m "Add your feature").
4. Push to the branch (git push origin feature/your-feature).
5. Open a pull request.

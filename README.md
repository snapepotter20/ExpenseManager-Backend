# Mini Expense Manager - Backend

Spring Boot backend for managing expenses, vendor-category rules, CSV ingestion, anomaly detection, and dashboard summaries.

## Technologies Used
- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA (Hibernate)
- PostgreSQL
- Apache Commons CSV
- Maven

## Setup Instructions

### 1. Prerequisites
- Java 17
- Maven
- PostgreSQL (recommended 16+)

### 2. Create Database
Run in PostgreSQL:

```sql
CREATE DATABASE expense_manager;
```

### 3. Configure DB Credentials
Update application.properties if needed:

spring.datasource.url=jdbc:postgresql://localhost:5432/expense_manager
spring.datasource.username=postgres
spring.datasource.password=postgres

### 4. Run Backend
cd backend
mvn spring-boot:run
Backend starts on: http://localhost:8080

API Summary
- POST /api/expenses - Add single expense
- POST /api/expenses/upload - Upload CSV (date,amount,vendorName,description)
- GET /api/expenses - List all expenses
- GET /api/expenses/dashboard?month=YYYY-MM - Dashboard data
- GET /api/vendor-rules - List vendor-category rules
- POST /api/vendor-rules - Add/update vendor rule
Assumptions
If vendor is not found in rules, category defaults to OTHER.
CSV contains valid headers: date,amount,vendorName,description.
Anomaly threshold is based on category average across stored records.
Initial seed vendor rules are inserted from data.sql.
PostgreSQL is running locally on default port 5432.

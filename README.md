# Spring Boot User Service — REST API with Liquibase, PostgreSQL, and CRUD Operations

This project is a **Spring Boot 3.5.6 (Java 17)** application built with **Gradle**, featuring REST endpoints for CRUD operations on a `User` entity.

---

## 🚀 Features

- CRUD REST API for `User`
- Liquibase-based schema migration

---




##  CRUD Test Commands (Windows-friendly)

### 1️⃣ Create User (`POST /createUser`)
✅ Positive:
```cmd
curl -X POST "http://localhost:8080/api/v1/users/createUser" ^
  -H "Content-Type: application/json" ^
  -d "{ \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"address\": \"123 Main St\" }"
```
❌ Negative (missing required field):
```cmd
curl -X POST "http://localhost:8080/api/v1/users/createUser" ^
  -H "Content-Type: application/json" ^
  -d "{ \"email\": \"missingname@example.com\" }"
```

### 2️⃣ Get All Users (`GET /getAllUsers`)
✅ Positive:
```cmd
curl -X GET "http://localhost:8080/api/v1/users/getAllUsers"
```

### 3️⃣ Get User By ID (`GET /getUserById/{id}`)
✅ Positive:
```cmd
curl -X GET "http://localhost:8080/api/v1/users/getUserById/1"
```
❌ Negative:
```cmd
curl -X GET "http://localhost:8080/api/v1/users/getUserById/9999"
```

### 4️⃣ Update User (`PUT /updateUser/{id}`)
✅ Positive:
```cmd
curl -X PUT "http://localhost:8080/api/v1/users/updateUser/1" ^
  -H "Content-Type: application/json" ^
  -d "{ \"name\": \"John Updated\", \"email\": \"john.updated@example.com\", \"address\": \"456 Updated Ave\" }"
```
❌ Negative:
```cmd
curl -X PUT "http://localhost:8080/api/v1/users/updateUser/9999" ^
  -H "Content-Type: application/json" ^
  -d "{ \"name\": \"Fake User\", \"email\": \"fake@example.com\", \"address\": \"Nowhere\" }"
```

### 5️⃣ Delete User (`DELETE /deleteUser/{id}`)
✅ Positive:
```cmd
curl -X DELETE "http://localhost:8080/api/v1/users/deleteUser/1"
```
❌ Negative:
```cmd
curl -X DELETE "http://localhost:8080/api/v1/users/deleteUser/9999"
```
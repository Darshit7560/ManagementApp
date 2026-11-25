# Student Management System — Spring Boot + Spring Security + MySQL

A Java 21 / Spring Boot 3.3 REST API demonstrating CRUD, JWT authentication,
role-based authorization, validation, pagination, DTOs, transactions and global exception handling.

## 1. Prerequisites
- JDK 21
- Maven 3.8+
- MySQL 8+
- IntelliJ IDEA (or Eclipse)

## 2. Database setup
Create a MySQL database (or let the JDBC URL create it):
```sql
CREATE DATABASE student_management;
```
Update `spring.datasource.username` and `spring.datasource.password` in
`src/main/resources/application.properties`. For production, keep credentials
and JWT secrets in environment variables or a secrets manager.

Run from the project root:
```bash
mvn clean spring-boot:run
```
Hibernate creates/updates tables for this learning project. In production, use
Flyway/Liquibase migrations and `ddl-auto=validate`.

## 3. Default admin and registration
A first-run admin is seeded:
- username: `admin`
- password: `Admin@12345`

Change this immediately outside local learning. `POST /api/auth/register`
creates a USER account; public registration cannot assign ADMIN.

## 4. API flow
1. Login with `POST /api/auth/login`.
2. Copy `accessToken`.
3. Send `Authorization: Bearer <token>` on protected endpoints.
4. ADMIN can create, update and delete. ADMIN and USER can read.

### Register
`POST http://localhost:8080/api/auth/register`
```json
{"username":"studentuser","password":"Student@123"}
```

### Login
`POST http://localhost:8080/api/auth/login`
```json
{"username":"admin","password":"Admin@12345"}
```
Response:
```json
{"accessToken":"<JWT>","tokenType":"Bearer"}
```

### Create student (ADMIN)
`POST /api/students`
```json
{
  "firstName":"Aarav",
  "lastName":"Patel",
  "email":"aarav@example.com",
  "course":"Computer Engineering",
  "dateOfBirth":"2002-05-14"
}
```

### Read
- `GET /api/students`
- `GET /api/students/1`
- `GET /api/students?page=0&size=5&sortBy=firstName`

### Update (ADMIN)
`PUT /api/students/1` with the same JSON structure.

### Delete (ADMIN)
`DELETE /api/students/1` — returns HTTP 204.

## 5. HTTP status codes
- 200 OK: successful read/update/login
- 201 Created: student created or user registered
- 204 No Content: student deleted
- 400 Bad Request: invalid request / validation / duplicate email
- 401 Unauthorized: missing, invalid or expired JWT
- 403 Forbidden: authenticated USER tries an ADMIN-only operation
- 404 Not Found: student ID does not exist

## 6. Architecture
Controller -> Service -> Repository -> MySQL.
- Controller: HTTP mapping and request/response.
- Service: business rules and transaction boundary.
- Repository: Spring Data JPA persistence.
- Entity: database representation.
- DTO: API contract; do not expose JPA entities directly.
- Security filter: validates JWT and establishes SecurityContext.
- Global advice: consistent error responses.

## 7. Important learning / production notes
- BCrypt hashes passwords; never store plaintext passwords.
- JWT is signed, not encrypted. Do not put secrets or sensitive personal data in its claims.
- This demo reloads the user from the database for each request, so current roles are checked.
- Use HTTPS, a strong externalized JWT secret, token rotation/revocation strategy, rate limiting,
  audit logging, database migrations, and integration tests in production.
- Restrict `sortBy` to an allow-list in a public production API. Add search/filtering as needed.
- CSRF is disabled because this API uses stateless bearer tokens and does not use cookie-based auth.

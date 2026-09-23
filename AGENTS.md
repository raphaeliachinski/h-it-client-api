# AGENTS.md

## Default Stack

- Java 21, Spring Boot 4, Spring Data JPA, H2, Liquibase, Spring security (JWT), Bean Validation, JUnit 5, Mockito.


## Requirements

- Create a Java + Spring API that supports CRUD (Create, Read, Update, Delete) for Clients.

- Client Entity must include the following fields:
  - Id
  - FirstName
  - LastName
  - Tax Identifier
  - Email
  - PhoneNumber
  - Documents (one-to-many relationship)

- Document Entity must include:
  - Id
  - Number
  - Description
  - Expiration Date

- Database:
  - Use an H2 in-memory database

- Ensure Clients and Documents are persisted in a single transaction (atomicity).
- The database schema should be created automatically, or at least provide a script to create it (Liquibase or create in your application.yaml)
- Build Tool:
- You may use Gradle or Maven to build and manage the project.

**Testing:**

- Implement at least one unit test (happy path) for each API endpoint.
- APIs:
  - Implement POST, GET, PUT, DELETE endpoints for Clients.
  - Additionally, create a Login API (for JWT purposes if implemented).

## Key Commands
- Build: `./mvnw -q compile`
- Tests: `./mvnw -q test`
- Run: `./mvnw spring-boot:run`

## Package structure

- Base package: `com.humanit.clientapi`
- Folders:
  - `domain`: JPA entities
  - `repository`:  spring data interfaces
  - `service`:  service layer (business rules)
  - `controller`: controllers REST, DTOs and GlobalExepctionHandler
  - `config`:  infra configs
  - `security`: JWT and spring security settings 
- DTOs are `record` with bean validation annotations.
- Entities should not be exposed to controllers always converts to DTO


## Domain model
- Client Entity must include the following fields:
  id, firstName, fastName, taxIdentifier, email, phoneNumber, documents (one-to-many relationship),
- Document Entity must include:
  id, number, description, expirationDate

**Atomicity**: create or update a client with its docs must be in a single transaction.

## Endpoints

| Método | Rota | Descrição                                                           |
|---|---|---------------------------------------------------------------------|
| POST | `/api/auth/login` | receives `{username, password}`, returns `{token, type, expiresIn}` |
| POST | `/api/clients` | create client (with documents) => 201 + `Location`                  |
| GET | `/api/clients` | list all clients                                                    |
| GET | `/api/clients/{id}` | search by id =>  404 if not found                                   |
| PUT | `/api/clients/{id}` | update client and replace documents                                 |
| DELETE | `/api/clients/{id}` | delete client and its documents => 204                              |


## Project rules
- JPA relationship mapping always `fetch = FetchType.LAZY`.
- Liquibase migrations, preferably in the sql format.
- Never use star import style.
- Always read the file before adding changes to avoid duplications.
- Do not create inner classes.
- Prefers the tool `write` for full replacement instead of `edit`
- Do not invent methods on existing classes. Open the class and check first.
- After ANY change to Java code, run `./mvnw -q test` and fix all errors before continuing.
- Import `jakarta.servlet` instead of `javax.servlet`

## JPA entities instructions
- Getters and setters for all fields
- No-args constructor and All-args constructor

## Tests
- Frameworks: JUnit + MockMvc + Mockito (spring-boot-starter-test, spring-security-test) 
- At lest one test per endpoint (6 endpoints == 6 tests), with @WebMvcTest
- One integration test testing proving atomicity when saving the client and its documents

## Version constraints — do NOT use pre-2023 APIs
- Spring Security 7: no WebSecurityConfigurerAdapter, no antMatchers,
  no authorizeRequests, no .and() chaining. Use a SecurityFilterChain
  bean with lambda DSL and requestMatchers.
- Servlet API: jakarta.servlet, never javax.servlet.
- JJWT 0.12: Jwts.builder().subject().expiration().signWith(key);
  Jwts.parser().verifyWith(key).build().parseSignedClaims(token).
  No setSubject, no parserBuilder, no signWith(key, algorithm).
- Jackson 3 (tools.jackson), not com.fasterxml.
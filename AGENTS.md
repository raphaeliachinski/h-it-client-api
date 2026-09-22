# AGENTS.md

## Default Stack

- Java 21, Spring Boot 4, Spring Data JPA, H2, Liquibase, Spring security (JWT), Bean Validation, JUnit 5, Mockito.

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


## Notes
- JPA relationship mapping always `fetch = FetchType.LAZY`.
- Liquibase migrations, preferably in the sql format.
- Never use star import style.
- Always read the file before adding changes to avoid duplications.

## JPA entities instructions
- Getters and setters for all fields
- Do not create inner classes 

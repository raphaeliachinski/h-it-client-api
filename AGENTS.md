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


## Notes
- JPA relationship mapping always `fetch = FetchType.LAZY`.
- Liquibase migrations, preferably in the sql format.
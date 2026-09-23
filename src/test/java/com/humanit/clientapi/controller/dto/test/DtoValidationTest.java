package com.humanit.clientapi.controller.dto.test;

import com.humanit.clientapi.controller.dto.ClientRequest;
import com.humanit.clientapi.controller.dto.DocumentRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DtoValidationTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // Test for DocumentRequest validation
    @Test
    public void testDocumentRequestValidation() {
        DocumentRequest invalid = new DocumentRequest("", "description", LocalDate.now());
        Set<ConstraintViolation<DocumentRequest>> violations = validator.validate(invalid);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must not be blank")));
    }

    // Test for ClientRequest validation
    @Test
    public void testClientRequestValidation() {
        ClientRequest invalid = new ClientRequest("", "Doe", "123", "invalid-email", "12345678901", List.of(new DocumentRequest("", "Passport", LocalDate.now())));
        Set<ConstraintViolation<ClientRequest>> violations = validator.validate(invalid);
        assertEquals(3, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must not be blank")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must be a well-formed email address")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must not be blank")));
    }

    // Test for ErrorResponse validation (if needed)
    @Test
    public void testErrorResponseValidation() {
        // ErrorResponse doesn't have constraints, so no validation needed
    }
}

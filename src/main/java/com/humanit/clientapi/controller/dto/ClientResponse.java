package com.humanit.clientapi.controller.dto;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public record ClientResponse(Long id, String firstName, String lastName, String taxIdentifier, String email, String phoneNumber, List<DocumentResponse> documents) {
}

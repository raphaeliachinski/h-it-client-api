package com.humanit.clientapi.controller.dto;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record DocumentResponse(Long id, String number, String description, LocalDate expirationDate) {
}

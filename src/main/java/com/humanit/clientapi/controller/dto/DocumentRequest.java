package com.humanit.clientapi.controller.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record DocumentRequest(@NotBlank @Size(max = 60) String number, @Size(max = 255) String description, @NotNull LocalDate expirationDate) {
}

package com.humanit.clientapi.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record ClientRequest(@NotBlank @Size(max = 100) String firstName,
                            @NotBlank @Size(max = 100) String lastName,
                            @NotBlank @Size(max = 30) String taxIdentifier,
                            @NotBlank @Email @Size(max = 180) String email,
                            @NotBlank @Size(max = 30) String phoneNumber,
                            @Valid List<DocumentRequest> documents
) {
}

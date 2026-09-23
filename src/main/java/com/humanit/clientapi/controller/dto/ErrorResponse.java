package com.humanit.clientapi.controller.dto;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(int status, String error, String message, Instant timestamp, Map<String, String> fieldErrors) {
}

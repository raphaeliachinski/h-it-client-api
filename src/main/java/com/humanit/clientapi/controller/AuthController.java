package com.humanit.clientapi.controller;

import com.humanit.clientapi.config.JwtConfig;
import com.humanit.clientapi.security.JwtUtil;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final JwtUtil jwtUtil;
    private final JwtConfig jwtConfig;

    public AuthController(JwtUtil jwtUtil, JwtConfig jwtConfig) {
        this.jwtUtil = jwtUtil;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // Dummy authentication: assume any username and password are valid
        String token = jwtUtil.generateToken(request.username());
        return ResponseEntity.ok(new LoginResponse(token, "Bearer", jwtConfig.getExpiration()));
    }

    public record LoginRequest(@NotNull String username, @NotNull String password) {}
    public record LoginResponse(String token, String type, long expiresIn) {}
}

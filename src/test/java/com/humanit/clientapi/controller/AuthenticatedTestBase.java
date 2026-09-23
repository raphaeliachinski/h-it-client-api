package com.humanit.clientapi.controller;

import com.humanit.clientapi.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(controllers = {AuthController.class, ClientController.class})
public class AuthenticatedTestBase {

    @Autowired
    protected MockMvc mockMvc;

    protected String authToken;

    @MockitoBean
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setup() throws Exception {
        // Mock login process to obtain a token
        String json = "{\"username\": \"user\", \"password\": \"password\"}";
        this.authToken = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn().getResponse().getContentAsString();

        // This is a dummy token for test purposes, will be replaced by real one once actual DB auth is implemented
        System.out.println("TOKENNNN => " + this.authToken);
         this.authToken = "Bearer dummy-jwt-token";
    }
}

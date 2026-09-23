package com.humanit.clientapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.humanit.clientapi.controller.dto.ClientRequest;
import com.humanit.clientapi.controller.dto.ClientResponse;
import com.humanit.clientapi.controller.dto.DocumentRequest;
import com.humanit.clientapi.controller.dto.DocumentResponse;
import com.humanit.clientapi.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private JsonMapper jsonMapper;

    @MockitoBean
    private ClientService clientService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    private ClientRequest sampleRequest;
    private ClientResponse sampleResponse;

    @BeforeEach
    void setUp() {
        DocumentRequest doc1 = new DocumentRequest("12345678901", "RG", LocalDate.of(2025, 1, 1));
        DocumentRequest doc2 = new DocumentRequest("09876543210", "CPF", LocalDate.of(2026, 1, 1));

        sampleRequest = new ClientRequest("John", "Doe", "123.456.789-00", "john.doe@example.com", "11987654321", List.of(doc1, doc2));

        DocumentResponse docRes1 = new DocumentResponse(1L, "12345678901", "RG", LocalDate.of(2025, 1, 1));
        DocumentResponse docRes2 = new DocumentResponse(2L, "09876543210", "CPF", LocalDate.of(2026, 1, 1));

        sampleResponse = new ClientResponse(1L, "John", "Doe", "123.456.789-00", "john.doe@example.com", "11987654321", List.of(docRes1, docRes2));
    }

    @Test
    void createClient_returns201WithLocation() throws Exception {
        given(clientService.save(any(ClientRequest.class))).willReturn(sampleResponse);

        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(sampleRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/clients/1")))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.documents").isArray())
                .andExpect(jsonPath("$.documents").value(hasSize(2)));
    }

    @Test
    void listClients_returns200() throws Exception {
        given(clientService.findAll()).willReturn(List.of(sampleResponse));

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].taxIdentifier").value("123.456.789-00"));
    }

    @Test
    void getClientById_returns200() throws Exception {
        given(clientService.findById(1L)).willReturn(sampleResponse);

        mockMvc.perform(get("/api/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.documents[0].id").value(1));
    }

    @Test
    void updateClient_returns200() throws Exception {
        given(clientService.update(eq(1L), any(ClientRequest.class))).willReturn(sampleResponse);

        mockMvc.perform(put("/api/clients/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(sampleRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void deleteClient_returns204() throws Exception {
        mockMvc.perform(delete("/api/clients/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(clientService).deleteById(1L);
    }
}
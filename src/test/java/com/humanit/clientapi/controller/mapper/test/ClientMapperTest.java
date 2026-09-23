package com.humanit.clientapi.controller.mapper.test;

import com.humanit.clientapi.controller.dto.ClientRequest;
import com.humanit.clientapi.controller.dto.ClientResponse;
import com.humanit.clientapi.controller.dto.DocumentRequest;
import com.humanit.clientapi.controller.dto.DocumentResponse;
import com.humanit.clientapi.domain.Client;
import com.humanit.clientapi.domain.Document;
import com.humanit.clientapi.controller.mapper.ClientMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClientMapperTest {

    @InjectMocks
    private ClientMapper clientMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToEntity_ClientRequestToClient() {
        // Given
        ClientRequest request = new ClientRequest("John", "Doe", "123456789", "john@example.com", "1234567890", List.of(new DocumentRequest("DOC123", "Passport", LocalDate.now())));
        // When
        Client client = clientMapper.toEntity(request);

        // Then
        assertEquals("John", client.getFirstName());
        assertEquals("Doe", client.getLastName());
        assertEquals("123456789", client.getTaxIdentifier());
        assertEquals("john@example.com", client.getEmail());
        assertEquals("1234567890", client.getPhoneNumber());
        assertEquals(1, client.getDocuments().size());
        assertEquals("DOC123", client.getDocuments().get(0).getNumber());
        assertEquals("Passport", client.getDocuments().get(0).getDescription());
    }

    @Test
    public void testToResponse_ClientToClientResponse() {
        // Given
        Client client = new Client();
        client.setId(1L);
        client.setFirstName("John");
        client.setLastName("Doe");
        client.setTaxIdentifier("123456789");
        client.setEmail("john@example.com");
        client.setPhoneNumber("1234567890");
        List<Document> documents = new ArrayList<>();
        Document doc = new Document();
        doc.setId(1L);
        doc.setNumber("DOC123");
        doc.setDescription("Passport");
        documents.add(doc);
        client.setDocuments(documents);

        // When
        ClientResponse response = clientMapper.toResponse(client);

        // Then
        assertEquals(1L, response.id());
        assertEquals("John", response.firstName());
        assertEquals("Doe", response.lastName());
        assertEquals("123456789", response.taxIdentifier());
        assertEquals("john@example.com", response.email());
        assertEquals("1234567890", response.phoneNumber());
        assertEquals(1, response.documents().size());
        assertEquals("DOC123", response.documents().get(0).number());
        assertEquals("Passport", response.documents().get(0).description());
    }

    @Test
    public void testCopyInto_ClientRequestToClient() {
        // Given
        Client client = new Client();
        client.setId(1L);
        client.setFirstName("OldName");
        client.setLastName("OldLastName");
        client.setTaxIdentifier("OldTaxID");
        client.setEmail("old@example.com");
        client.setPhoneNumber("OldPhone");
        List<Document> oldDocs = new ArrayList<>();
        oldDocs.add(new Document());
        client.setDocuments(oldDocs);

        ClientRequest request = new ClientRequest("NewName", "NewLastName", "NewTaxID", "new@example.com", "NewPhone", List.of(new DocumentRequest("NEWDOC", "NewDoc", LocalDate.now())));

        // When
        clientMapper.copyInto(request, client);

        // Then
        assertEquals("NewName", client.getFirstName());
        assertEquals("NewLastName", client.getLastName());
        assertEquals("NewTaxID", client.getTaxIdentifier());
        assertEquals("new@example.com", client.getEmail());
        assertEquals("NewPhone", client.getPhoneNumber());
        assertEquals(1, client.getDocuments().size());
        assertEquals("NEWDOC", client.getDocuments().get(0).getNumber());
        assertEquals("NewDoc", client.getDocuments().get(0).getDescription());
    }
}

package com.humanit.clientapi.controller.mapper;

import com.humanit.clientapi.controller.dto.ClientRequest;
import com.humanit.clientapi.controller.dto.ClientResponse;
import com.humanit.clientapi.domain.Client;
import com.humanit.clientapi.domain.Document;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapper {
    public Client toEntity(ClientRequest request) {
        Client client = new Client();
        client.setFirstName(request.firstName());
        client.setLastName(request.lastName()); // Assuming lastName is the correct field in entity
        client.setTaxIdentifier(request.taxIdentifier());
        client.setEmail(request.email());
        client.setPhoneNumber(request.phoneNumber());
        client.getDocuments().clear();
        client.getDocuments().addAll(request.documents().stream()
                .map(this::toEntity)
                .collect(Collectors.toList()));
        return client;
    }

    public ClientResponse toResponse(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getTaxIdentifier(),
                client.getEmail(),
                client.getPhoneNumber(),
                client.getDocuments().stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList()));
    }

    public void copyInto(ClientRequest request, Client client) {
        client.setFirstName(request.firstName());
        client.setLastName(request.lastName()); // Assuming astName is the correct field in entity
        client.setTaxIdentifier(request.taxIdentifier());
        client.setEmail(request.email());
        client.setPhoneNumber(request.phoneNumber());
        client.getDocuments().clear();
        client.getDocuments().addAll(request.documents().stream()
                .map(this::toEntity)
                .collect(Collectors.toList()));
    }

    private Document toEntity(com.humanit.clientapi.controller.dto.DocumentRequest request) {
        Document document = new Document();
        document.setNumber(request.number());
        document.setDescription(request.description());
        document.setExpirationDate(request.expirationDate());
        return document;
    }

    private com.humanit.clientapi.controller.dto.DocumentResponse toResponse(Document document) {
        return new com.humanit.clientapi.controller.dto.DocumentResponse(
                document.getId(),
                document.getNumber(),
                document.getDescription(),
                document.getExpirationDate());
    }
}

package com.humanit.clientapi.service;

import com.humanit.clientapi.controller.dto.ClientRequest;
import com.humanit.clientapi.controller.dto.ClientResponse;
import com.humanit.clientapi.controller.mapper.ClientMapper;
import com.humanit.clientapi.domain.Client;
import com.humanit.clientapi.domain.Document;
import com.humanit.clientapi.repository.ClientRepository;
import com.humanit.clientapi.service.exception.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Transactional
    public ClientResponse save(ClientRequest request) {
        Client client = clientMapper.toEntity(request);
        List<Document> documents = request.documents().stream()
                .map(doc -> new Document(doc.number(), doc.description(), doc.expirationDate(), client))
                .toList();
        client.setDocuments(documents);
        Client savedClient = clientRepository.save(client);
        return clientMapper.toResponse(savedClient);
    }

    @Transactional
    public ClientResponse update(Long id, ClientRequest request) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isEmpty()) {
            throw new ClientNotFoundException(id);
        }
        Client client = optionalClient.get();
        clientMapper.copyInto(request, client);
        List<Document> documents = request.documents().stream()
                .map(doc -> new Document(doc.number(), doc.description(), doc.expirationDate(), client))
                .toList();
        client.setDocuments(documents);
        Client updatedClient = clientRepository.save(client);
        return clientMapper.toResponse(updatedClient);
    }

    public List<ClientResponse> findAll() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toResponse)
                .toList();
    }

    public ClientResponse findById(Long id) {
        return clientRepository.findById(id)
                .map(clientMapper::toResponse)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException(id);
        }
        clientRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        clientRepository.deleteAll();
    }
}

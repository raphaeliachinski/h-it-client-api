package com.humanit.clientapi.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private String description;
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Document() {
    }

    public Document(String number, String description, LocalDate expirationDate, Client client) {
        this.number = number;
        this.description = description;
        this.expirationDate = expirationDate;
        this.client = client;
    }

    public Document(Long id, String number, String description, LocalDate expirationDate, Client client) {
        this.id = id;
        this.number = number;
        this.description = description;
        this.expirationDate = expirationDate;
        this.client = client;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
}
package com.humanit.clientapi.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String fastName;
    private String taxIdentifier;
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getFastName() { return fastName; }
    public void setFastName(String fastName) { this.fastName = fastName; }

    public String getTaxIdentifier() { return taxIdentifier; }
    public void setTaxIdentifier(String taxIdentifier) { this.taxIdentifier = taxIdentifier; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public List<Document> getDocuments() { return documents; }
    public void setDocuments(List<Document> documents) { this.documents = documents; }

    // Add document method
    public void addDocument(Document document) {
        documents.add(document);
        document.setClient(this);
    }
}
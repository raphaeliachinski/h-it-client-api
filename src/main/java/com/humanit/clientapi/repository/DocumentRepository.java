package com.humanit.clientapi.repository;

import com.humanit.clientapi.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    // No custom methods needed for documents (basic CRUD operations suffice)
}
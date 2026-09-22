package com.humanit.clientapi.repository;

import com.humanit.clientapi.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByTaxIdentifier(String taxIdentifier);
    Optional<Client> findByTaxIdentifier(String taxIdentifier);
}
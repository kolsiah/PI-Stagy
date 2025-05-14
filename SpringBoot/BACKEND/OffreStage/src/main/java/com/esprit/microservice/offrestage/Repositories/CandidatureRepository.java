package com.esprit.microservice.offrestage.Repositories;

import com.esprit.microservice.offrestage.Entities.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {
    // Rechercher par l'ID de l'utilisateur (étudiant) qui est stocké dans 'userId'
    List<Candidature> findByUserId(Long userId);
}

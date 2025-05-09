package com.esprit.microservice.offrestage.Repositories;

import com.esprit.microservice.offrestage.Entities.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    // Exemple : Récupérer toutes les interviews d’une candidature
    List<Interview> findByCandidatureId(Long candidatureId);
    List<Interview> findByStatus(String status);
}

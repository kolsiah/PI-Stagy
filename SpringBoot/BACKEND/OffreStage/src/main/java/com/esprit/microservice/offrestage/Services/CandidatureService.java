package com.esprit.microservice.offrestage.Services;

import com.esprit.microservice.offrestage.Entities.Candidature;
import com.esprit.microservice.offrestage.Entities.Interview;
import com.esprit.microservice.offrestage.DTO.UserDTO;

import java.util.List;
import java.util.Optional;

public interface CandidatureService {

    Candidature postuler(Candidature candidature);

    // Modifier cette méthode pour utiliser userId au lieu de etudiantId
    List<Candidature> getCandidaturesParUser(Long userId);

    Optional<Candidature> getCandidatureParId(Long id);

    Candidature mettreAJourEtat(Long id, String etat);
}
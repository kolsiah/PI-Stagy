package com.esprit.microservice.offrestage.Services;

import com.esprit.microservice.offrestage.Client.UserClient;
import com.esprit.microservice.offrestage.DTO.UserDTO;
import com.esprit.microservice.offrestage.Entities.Candidature;
import com.esprit.microservice.offrestage.Entities.Interview;
import com.esprit.microservice.offrestage.Entities.OffreStage;
import com.esprit.microservice.offrestage.Repositories.CandidatureRepository;
import com.esprit.microservice.offrestage.Repositories.InterviewRepository;
import com.esprit.microservice.offrestage.Repositories.OffreStageRepository;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CandidatureServiceImpl implements CandidatureService {



    @Autowired
    private CandidatureRepository candidatureRepository;

    @Autowired
    private OffreStageRepository offreStageRepository;






    @Autowired
    private UserClient userClient;  // Utilisation de UserClient pour récupérer les informations de l'utilisateur

    @Override
    public Candidature postuler(Candidature candidature) {
        // Validate that cvPath is not null or empty
        if (candidature.getCvPath() == null || candidature.getCvPath().isEmpty()) {
            throw new IllegalArgumentException("Le champ 'cvPath' est obligatoire.");
        }

        // Retrieve the associated OffreStage
        Optional<OffreStage> offreStageOptional = offreStageRepository.findById(candidature.getOffreStage().getId());
        if (offreStageOptional.isEmpty()) {
            throw new IllegalArgumentException("L'offre de stage n'existe pas.");
        }
        candidature.setOffreStage(offreStageOptional.get());

        // Set default values for candidature
        candidature.setDatePostulation(LocalDateTime.now());
        candidature.setEtat("En attente");

        // Retrieve user details using UserClient
        Long userId = candidature.getUserId();
        UserDTO userDTO = userClient.getUserById(userId);
        candidature.setUser(userDTO);

        // Ensure the id is null to avoid Hibernate trying to update an existing entity
        candidature.setId(null);

        // Save the candidature
        return candidatureRepository.save(candidature);
    }

    @Override
    public List<Candidature> getCandidaturesParUser(Long userId) {
        // Utiliser findByUserId directement
        List<Candidature> candidatures = candidatureRepository.findByUserId(userId);

        // Récupérer les informations utilisateur et les ajouter à chaque candidature
        for (Candidature candidature : candidatures) {
            UserDTO userDTO = userClient.getUserById(candidature.getUserId());
            candidature.setUser(userDTO);
        }

        return candidatures;
    }


    @Override
    public Optional<Candidature> getCandidatureParId(Long id) {
        return candidatureRepository.findById(id);
    }

    @Override
    public Candidature mettreAJourEtat(Long id, String etat) {
        Optional<Candidature> candidatureOptional = candidatureRepository.findById(id);

        if (candidatureOptional.isPresent()) {
            Candidature candidature = candidatureOptional.get();
            candidature.setEtat(etat);

            return candidatureRepository.save(candidature);


        }

        return null;
    }
}

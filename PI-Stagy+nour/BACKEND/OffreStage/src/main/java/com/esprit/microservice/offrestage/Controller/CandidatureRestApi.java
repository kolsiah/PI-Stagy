package com.esprit.microservice.offrestage.Controller;

import com.esprit.microservice.offrestage.Entities.Candidature;
import com.esprit.microservice.offrestage.Entities.Interview;
import com.esprit.microservice.offrestage.Services.CandidatureService;
import com.esprit.microservice.offrestage.Services.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/candidatures")
public class CandidatureRestApi {

    @Autowired
    private CandidatureService candidatureService;
    private InterviewService interviewService;

    @PostMapping
    public Candidature postuler(@RequestBody Candidature candidature) {
        if (candidature.getCvPath() == null || candidature.getCvPath().isEmpty()) {
            throw new IllegalArgumentException("Le champ 'cvPath' est obligatoire.");
        }
        return candidatureService.postuler(candidature);
    }

    @GetMapping("/user/{userId}")
    public List<Candidature> getCandidaturesByUser(@PathVariable Long userId) {
        return candidatureService.getCandidaturesParUser(userId);
    }
    @GetMapping("/{id}")
    public Optional<Candidature> getCandidature(@PathVariable Long id) {
        return candidatureService.getCandidatureParId(id);
    }

    @PutMapping("/{id}")
    public Candidature mettreAJourEtat(@PathVariable Long id, @RequestBody CandidatureUpdateRequest updateRequest) {
        // Appel du service avec l'état et les détails de l'entretien
        return candidatureService.mettreAJourEtat(id, updateRequest.getEtat());
    }


    @GetMapping("/allInterview")
    public List<Interview> getInterviewsBy() {
        return interviewService.getAllInterviews();
    }

    // Classe pour encapsuler les détails de la requête (état et interviewDetails)
    public static class CandidatureUpdateRequest {
        private String etat;
        private Interview interviewDetails;


        // Getters et Setters
        public String getEtat() {
            return etat;
        }

        public void setEtat(String etat) {
            this.etat = etat;
        }

        public Interview getInterviewDetails() {
            return interviewDetails;
        }

        public void setInterviewDetails(Interview interviewDetails) {
            this.interviewDetails = interviewDetails;
        }
    }
}

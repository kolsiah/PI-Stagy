package com.example.entreprise.services;

import com.example.entreprise.Repositories.RatingRepository;
import com.example.entreprise.Repositories.EntrepriseRepository;
import com.example.entreprise.entities.Rating;
import com.example.entreprise.entities.Entreprise;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final EntrepriseRepository entrepriseRepository;

    public RatingService(RatingRepository ratingRepository, EntrepriseRepository entrepriseRepository) {
        this.ratingRepository = ratingRepository;
        this.entrepriseRepository = entrepriseRepository;
    }

    @Transactional
    public Rating ajouterRating(Rating rating) {
        // ✅ Fetch entreprise from DB first
        Long entrepriseId = rating.getEntreprise().getIdEntreprise();
        Entreprise entreprise = entrepriseRepository.findById(entrepriseId).orElse(null);

        if (entreprise == null) {
            throw new RuntimeException("🚨 Error: Entreprise with ID " + entrepriseId + " not found.");
        }

        rating.setEntreprise(entreprise); // ✅ Attach entreprise to rating
        Rating savedRating = ratingRepository.save(rating);

        // ✅ Update moyenneNote
        entreprise.updateMoyenneNote();
        entrepriseRepository.save(entreprise);

        return savedRating;
    }

    public List<Rating> getRatingsByEntreprise(Long entrepriseId) {
        return ratingRepository.findByEntreprise_IdEntreprise(entrepriseId).isEmpty() ?
                List.of() :
                ratingRepository.findByEntreprise_IdEntreprise(entrepriseId);
    }

    public Double getAverageRating(Long entrepriseId) {
        return ratingRepository.findAverageRatingByEntreprise(entrepriseId) != null ?
                ratingRepository.findAverageRatingByEntreprise(entrepriseId) : 0.0;
    }
}

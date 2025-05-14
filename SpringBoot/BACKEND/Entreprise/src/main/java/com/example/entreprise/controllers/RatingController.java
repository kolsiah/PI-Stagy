package com.example.entreprise.controllers;

import com.example.entreprise.entities.Rating;
import com.example.entreprise.services.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@CrossOrigin(origins = "http://localhost:4200") // ✅ Allow frontend requests
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    // ✅ Add a new rating
    @PostMapping
    public ResponseEntity<?> ajouterRating(@RequestBody Rating rating) {
        // ✅ Debugging logs
        System.out.println("Received rating request: " + rating);

        if (rating == null) {
            return ResponseEntity.badRequest().body("🚨 Error: Rating object is missing in request.");
        }

        if (rating.getEntreprise() == null) {
            return ResponseEntity.badRequest().body("🚨 Error: Entreprise object is missing in request.");
        }

        if (rating.getEntreprise().getIdEntreprise() == null) {
            return ResponseEntity.badRequest().body("🚨 Error: Entreprise ID is required.");
        }

        Rating savedRating = ratingService.ajouterRating(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
    }




    // ✅ Get all ratings for a specific entreprise
    @GetMapping("/entreprise/{id}")
    public ResponseEntity<List<Rating>> getRatingsByEntreprise(@PathVariable Long id) {
        List<Rating> ratings = ratingService.getRatingsByEntreprise(id);
        if (ratings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(List.of());
        }
        return ResponseEntity.ok(ratings);
    }

    // ✅ Get average rating for an entreprise
    @GetMapping("/entreprise/{id}/moyenne")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long id) {
        Double averageRating = ratingService.getAverageRating(id);
        return ResponseEntity.ok(averageRating);
    }
}

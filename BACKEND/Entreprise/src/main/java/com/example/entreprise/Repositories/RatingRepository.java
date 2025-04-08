package com.example.entreprise.Repositories;

import com.example.entreprise.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    // ✅ Get all ratings for a specific entreprise
    List<Rating> findByEntreprise_IdEntreprise(Long entrepriseId);

    // ✅ Let the database calculate the average rating
    @Query("SELECT AVG(r.note) FROM Rating r WHERE r.entreprise.idEntreprise = :entrepriseId")
    Double findAverageRatingByEntreprise(@Param("entrepriseId") Long entrepriseId);
}

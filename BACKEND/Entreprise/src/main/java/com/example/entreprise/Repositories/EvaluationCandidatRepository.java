package com.example.entreprise.Repositories;

import com.example.entreprise.entities.EvaluationCandidat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EvaluationCandidatRepository extends JpaRepository<EvaluationCandidat, Long> {
    List<EvaluationCandidat> findByRatingAndDateEvaluation(int rating, LocalDateTime dateEvaluation);

}

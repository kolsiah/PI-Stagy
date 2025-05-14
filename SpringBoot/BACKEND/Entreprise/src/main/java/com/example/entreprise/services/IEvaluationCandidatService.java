package com.example.entreprise.services;

import com.example.entreprise.entities.EvaluationCandidat;
import java.time.LocalDateTime;
import java.util.List;

public interface IEvaluationCandidatService {

    // Ajouter une évaluation de candidat
    EvaluationCandidat addEvaluationCandidat(EvaluationCandidat evaluation);

    // Mettre à jour une évaluation de candidat
    EvaluationCandidat updateEvaluationCandidat(EvaluationCandidat evaluation);

    // Récupérer toutes les évaluations de candidats
    List<EvaluationCandidat> retrieveAllEvaluationsCandidats();

    // Récupérer une évaluation de candidat par ID
    EvaluationCandidat retrieveEvaluationCandidatById(Long idEval);

    // Supprimer une évaluation de candidat par ID
    void deleteEvaluationCandidatById(Long idEval);

    // Rechercher des évaluations selon la note et la date
    List<EvaluationCandidat> searchEvaluationsByRatingAndDate(int rating, LocalDateTime date);
}
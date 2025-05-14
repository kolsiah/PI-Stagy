package com.example.entreprise.services;

import com.example.entreprise.entities.EvaluationCandidat;
import com.example.entreprise.Repositories.EvaluationCandidatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationCandidatService implements IEvaluationCandidatService {

    @Autowired
    private EvaluationCandidatRepository evaluationCandidatRepository;

    @Override
    public EvaluationCandidat addEvaluationCandidat(EvaluationCandidat evaluation) {
        return evaluationCandidatRepository.save(evaluation);
    }

    @Override
    public EvaluationCandidat updateEvaluationCandidat(EvaluationCandidat evaluation) {
        return evaluationCandidatRepository.save(evaluation);
    }

    @Override
    public List<EvaluationCandidat> retrieveAllEvaluationsCandidats() {
        return evaluationCandidatRepository.findAll();
    }

    @Override
    public EvaluationCandidat retrieveEvaluationCandidatById(Long idEval) {
        return evaluationCandidatRepository.findById(idEval).orElse(null);
    }

    @Override
    public void deleteEvaluationCandidatById(Long idEval) {
        evaluationCandidatRepository.deleteById(idEval);
    }

    @Override
    public List<EvaluationCandidat> searchEvaluationsByRatingAndDate(int rating, LocalDateTime date) {
        return evaluationCandidatRepository.findByRatingAndDateEvaluation(rating, date);
    }
}
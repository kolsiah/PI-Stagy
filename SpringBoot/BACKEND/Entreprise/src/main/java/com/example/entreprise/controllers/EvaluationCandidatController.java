package com.example.entreprise.controllers;

import com.example.entreprise.entities.EvaluationCandidat;
import com.example.entreprise.services.IEvaluationCandidatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluation")
@AllArgsConstructor
@CrossOrigin("*")  // Ajout pour permettre les requêtes cross-origin
public class EvaluationCandidatController {

    private final IEvaluationCandidatService iEvaluationCandidatService;

    @PostMapping("/addEvaluationCandidat")
    public ResponseEntity<EvaluationCandidat> addEvaluationCandidat(@RequestBody EvaluationCandidat evaluation) {
        try {
            EvaluationCandidat saved = iEvaluationCandidatService.addEvaluationCandidat(evaluation);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateEvaluationCandidat")
    public ResponseEntity<EvaluationCandidat> updateEvaluationCandidat(@RequestBody EvaluationCandidat evaluation) {
        try {
            EvaluationCandidat updated = iEvaluationCandidatService.updateEvaluationCandidat(evaluation);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getAllEvaluationsCandidats")
    public ResponseEntity<List<EvaluationCandidat>> retrieveAllEvaluationsCandidats() {
        List<EvaluationCandidat> evaluations = iEvaluationCandidatService.retrieveAllEvaluationsCandidats();
        return ResponseEntity.ok(evaluations);
    }

    @GetMapping("/findEvaluationCandidatById/{idEval}")
    public ResponseEntity<EvaluationCandidat> retrieveEvaluationCandidatById(@PathVariable Long idEval) {
        try {
            EvaluationCandidat evaluation = iEvaluationCandidatService.retrieveEvaluationCandidatById(idEval);
            return ResponseEntity.ok(evaluation);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteEvaluationCandidatById/{idEval}")
    public ResponseEntity<Void> deleteEvaluationCandidatById(@PathVariable Long idEval) {
        try {
            iEvaluationCandidatService.deleteEvaluationCandidatById(idEval);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
package com.example.validationdocument.Controller;

import com.example.validationdocument.Entity.StatutV;
import com.example.validationdocument.Entity.Validation;
import com.example.validationdocument.Services.SentimentAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.validationdocument.Services.ValidationDocImpl;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/validations")
public class ValidationController {
    @Autowired
    private ValidationDocImpl validationDoc;
    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @PostMapping("/analyse-tous-commentaires")
    public ResponseEntity<Map<String, Long>> analyseTousCommentaires(@RequestBody List<String> commentaires) {
        return ResponseEntity.ok(sentimentAnalysisService.analyserCommentaires(commentaires));
    }

    @GetMapping("/analyse-commentaire/{idValidation}")
    public ResponseEntity<String> analyseCommentaire(@PathVariable int idValidation) {
        Validation validation = validationDoc.getValidation(idValidation);
        if (validation == null || validation.getCommentaire() == null) {
            return ResponseEntity.badRequest().body("Aucun commentaire à analyser");
        }

        String resultat = sentimentAnalysisService.analyserCommentaire(validation.getCommentaire());
        return ResponseEntity.ok(resultat);
    }
    @PostMapping()
    @Operation(summary = "Validate document")
    public ResponseEntity<String> validateDocument(
            @RequestParam("idDocument") int idDocument,
            @RequestParam("idEncadrant") int idEncadrant,
            @RequestParam("statut") String statut,
            @RequestParam("commentaire") String commentaire,
            @RequestParam("etudiantId") int etudiantId,    // ✅ ajouté
            @RequestParam("offreId") int offreId           // ✅ ajouté
    ) {
        try {
            StatutV s = StatutV.valueOf(statut.toLowerCase());
            validationDoc.validateDocument(idDocument, idEncadrant, s, commentaire, etudiantId, offreId);
            return ResponseEntity.ok("Document validated successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid statut value: " + statut);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error validating document: " + e.getMessage());
        }
    }

    @GetMapping("/encadrant/{id}")
    public ResponseEntity<List<Validation>> getValByIdEncadrant(@PathVariable("id") int id) {
        List<Validation> validations = validationDoc.getValidationsByIdEncadrant(id);
        return ResponseEntity.ok(validations);
    }

    @GetMapping("/etudiant/{id}")
    public ResponseEntity<List<Validation>> getValByIdEt(@PathVariable("id") int id) {
        List<Validation> validations = validationDoc.getValidationsByIdEtud(id);
        return ResponseEntity.ok(validations);
    }
    @GetMapping()
    @Operation(summary = "Retrieve all validations")
    public ResponseEntity<List<Validation>> getValidations() {
        List<Validation> validations = validationDoc.getValidations();
        return ResponseEntity.ok(validations);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Retrieve Validation by ID")
    public ResponseEntity<Validation> getValidationById(@PathVariable int id) {
        return ResponseEntity.ok(validationDoc.getValidation(id));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete validation by ID")
    public ResponseEntity<String> deleteValidationById(@PathVariable int id) {
        String cc = validationDoc.deleteValidation(id);
        return ResponseEntity.ok(cc);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update validation by ID")
    public ResponseEntity<String> updateValidationById(@PathVariable int id, @RequestBody Validation validation) {
        validationDoc.updateValidation(id, validation);
        return ResponseEntity.ok("Validation updated successfully!");
    }
}
package com.example.validationdocument.Controller;

import com.example.validationdocument.Entity.StatutV;
import com.example.validationdocument.Entity.Validation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.validationdocument.Services.ValidationDocImpl;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/validations")
public class ValidationController {
    @Autowired
    private ValidationDocImpl validationDoc;
    @PostMapping()
    @Operation(summary = "Validate document")
    public ResponseEntity<String> validateDocument(
            @RequestParam("idDocument") int idDocument,
            @RequestParam("idEncadrant") int idEncadrant,
            @RequestParam("statut") String statut,
            @RequestParam("commentaire") String commentaire
    ) {
        try {
            // Convert the statut to lowercase to match the enum values correctly
            StatutV s = StatutV.valueOf(statut.toLowerCase());  // Convert to lowercase if needed

            // Call the service method
            validationDoc.validateDocument(idDocument, idEncadrant, s, commentaire);
            return ResponseEntity.ok("Document validated successfully!");
        } catch (IllegalArgumentException e) {
            // Handle invalid statut value
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid statut value: " + statut);
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error validating document: " + e.getMessage());
        }
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
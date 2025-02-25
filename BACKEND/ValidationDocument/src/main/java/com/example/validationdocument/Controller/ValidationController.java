package com.example.validationdocument.Controller;

import com.example.validationdocument.Entity.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.validationdocument.Services.ValidationDocImpl;

@RestController
@RequestMapping("/validations")
public class ValidationController {
    @Autowired
    private ValidationDocImpl validationDoc;

    @PostMapping("/validate")
    public ResponseEntity<Validation> validateDocument(
            @RequestParam int documentId,
            @RequestParam int encadrantId,
            @RequestParam boolean isApproved,
            @RequestParam String commentaire) {

        Validation validation = validationDoc.validateDocument(documentId, encadrantId, isApproved, commentaire);
        return ResponseEntity.ok(validation);
    }
}
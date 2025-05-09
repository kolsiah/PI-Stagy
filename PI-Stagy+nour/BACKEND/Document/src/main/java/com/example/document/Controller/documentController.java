package com.example.document.Controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.document.Entity.Document;
import com.example.document.Entity.Statut;
import com.example.document.Entity.Type;
import com.example.document.Services.documentServiceIMPL;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/documents")
public class documentController {

    @Autowired
    private documentServiceIMPL documentService;

    @GetMapping
    @Operation(summary = "Retreive all documents")
    public ResponseEntity<List<Document>> getAllDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retreive document by ID")
    public ResponseEntity<Document> getDocumentById(@PathVariable int id) {
        return ResponseEntity.ok(documentService.getDocumentById(id));
    }
        @GetMapping("/etudiant/{id}")
    public ResponseEntity<List<Document>> getDocumentsByEtudiantId(@PathVariable int id) {
        List<Document> documents = documentService.getDocumentsByEtudiantId(id);
        return ResponseEntity.ok(documents);
    }
    @GetMapping("/enseignant/documents")
    public ResponseEntity<List<Document>> getAllDocumentsExceptCV() {
        return ResponseEntity.ok(documentService.getAllExceptCV());
    }

    @PostMapping
    @Operation(summary = "Insert new document")
    public ResponseEntity<Document> createDocument(@RequestBody Document document) {
        return ResponseEntity.ok(documentService.createDocument(document));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update document by ID")
    public ResponseEntity<Document> updateDocument(@PathVariable int id, @RequestBody Document document) {
        return ResponseEntity.ok(documentService.updateDocument(id, document));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete document by ID")
    public ResponseEntity<Void> deleteDocument(@PathVariable int id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Insert document with pdf File. It will be treated by an external Cloud Storage")
    public ResponseEntity<String> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "titreDocument", required = false) String titreDocument,
            @RequestParam("typeDocument") String typeDocumentStr,
            @RequestParam(value = "statutDocument", required = false) String statutDocumentStr,
            @RequestParam("etudiantId") int etudiantId,
            @RequestParam("stageId") int stageId
    ) {
        try {
            // ✅ Conversion sécurisée du type (sans upperCase)
            Type typeDocument;
            try {
                typeDocument = Type.valueOf(typeDocumentStr); // pas .toUpperCase()
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest().body("Invalid typeDocument value: " + typeDocumentStr);
            }

            // ✅ titreDocument requis sauf si type = cv
            if (!"cv".equals(typeDocumentStr) && (titreDocument == null || titreDocument.isBlank())) {
                return ResponseEntity.badRequest().body("titreDocument is required unless typeDocument is 'cv'");
            }

            // ✅ statutDocument optionnel
            Statut statutDocument = null;
            if (statutDocumentStr != null && !statutDocumentStr.isBlank()) {
                try {
                    statutDocument = Statut.valueOf(statutDocumentStr.toLowerCase()); // ⚠️ convertit vers minuscule
                } catch (IllegalArgumentException ex) {
                    return ResponseEntity.badRequest().body("Invalid statutDocument value: " + statutDocumentStr);
                }
            }

            // ✅ Upload fichier + sauvegarde document
            String fileUrl = documentService.uploadFile(file, titreDocument, typeDocument, statutDocument, etudiantId, stageId);
            return ResponseEntity.ok("File uploaded successfully: " + fileUrl);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }



    @PutMapping("/{id}/status")
    @Operation(summary = "Update document Status")
    public ResponseEntity<String> updateDocumentStatus(@PathVariable int id, @RequestParam String status) {
        boolean updated = documentService.updateDocumentStatus(id, status);
        if (updated) {
            return ResponseEntity.ok("Document status updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found.");
        }
    }
    @GetMapping("/analyze-report/{id}")
    public ResponseEntity<?> analyzeReport(@PathVariable int id) {
        try {
            Map<String, Object> result = documentService.analyzeReport(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'analyse du rapport : " + e.getMessage());
        }
    }
    @GetMapping("/compare/{id1}/{id2}")
    public ResponseEntity<?> compareDocuments(
            @PathVariable int id1,
            @PathVariable int id2) {
        try {
            float similarityScore = documentService.compareDocumentsById(id1, id2);
            boolean isPlagiarism = similarityScore >= 0.8;

            Map<String, Object> result = new HashMap<>();
            result.put("similarityScore", similarityScore);
            result.put("plagiarismSuspected", isPlagiarism);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur de comparaison : " + e.getMessage());
        }
    }

}

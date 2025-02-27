package com.example.document.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.document.Entity.Document;
import com.example.document.Entity.Statut;
import com.example.document.Entity.Type;
import com.example.document.Services.documentServiceIMPL;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/documents")
public class documentController {

    @Autowired
    private documentServiceIMPL documentService;

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments() {
        return ResponseEntity.ok(documentService.getAllDocuments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable int id) {
        return ResponseEntity.ok(documentService.getDocumentById(id));
    }

    @PostMapping
    public ResponseEntity<Document> createDocument(@RequestBody Document document) {
        return ResponseEntity.ok(documentService.createDocument(document));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable int id, @RequestBody Document document) {
        return ResponseEntity.ok(documentService.updateDocument(id, document));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable int id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("titreDocument") String titreDocument,
            @RequestParam("typeDocument") String typeDocumentStr,
            @RequestParam("statutDocument") String statutDocumentStr,
            @RequestParam("etudiantId") int etudiantId,
            @RequestParam("stageId") int stageId
    ) {
        try {
            // Convert String to Enum (you need to catch possible errors here if enums are wrong)
            Type typeDocument = Type.valueOf(typeDocumentStr);
            Statut statutDocument = Statut.valueOf(statutDocumentStr);

            // Call the service to upload the file and save the document
            String fileUrl = documentService.uploadFile(file, titreDocument, typeDocument, statutDocument, etudiantId, stageId);

            return ResponseEntity.ok("File uploaded successfully: " + fileUrl);
        } catch (IOException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

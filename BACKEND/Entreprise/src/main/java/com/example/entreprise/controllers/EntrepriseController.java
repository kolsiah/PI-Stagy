package com.example.entreprise.controllers;

import com.example.entreprise.entities.Entreprise;
import com.example.entreprise.services.IEntrepriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/entreprises")
@CrossOrigin(origins = "http://localhost:4200") // ✅ Allow Angular Frontend
public class EntrepriseController {

    @Autowired
    private IEntrepriseService entrepriseService;

    // ✅ Add a New Entreprise
    @PostMapping("/add")
    public ResponseEntity<Entreprise> addEntreprise(@RequestBody Entreprise entreprise) {
        return ResponseEntity.ok(entrepriseService.addEntreprise(entreprise));
    }

    // ✅ Update Existing Entreprise
    @PutMapping("/update")
    public ResponseEntity<Entreprise> updateEntreprise(@RequestBody Entreprise entreprise) {
        return ResponseEntity.ok(entrepriseService.updateEntreprise(entreprise));
    }

    // ✅ Retrieve All Entreprises
    @GetMapping("/retrieveAllEntreprises")
    public ResponseEntity<List<Entreprise>> getAllEntreprises() {
        List<Entreprise> entreprises = entrepriseService.retrieveAllEntreprises();
        return ResponseEntity.ok(entreprises != null ? entreprises : List.of());
    }

    // ✅ Retrieve Entreprise by ID (Fixed URL Path)
    @GetMapping("/retrieveEntrepriseById/{id}")
    public ResponseEntity<Entreprise> retrieveEntrepriseById(@PathVariable Long id) {
        Entreprise entreprise = entrepriseService.retrieveEntrepriseById(id);
        return entreprise != null ? ResponseEntity.ok(entreprise) : ResponseEntity.notFound().build();
    }

    // ✅ Delete Entreprise by ID
    @DeleteMapping("/deleteEntrepriseById/{id}")
    public ResponseEntity<String> deleteEntrepriseById(@PathVariable Long id) {
        if (entrepriseService.retrieveEntrepriseById(id) != null) {
            entrepriseService.deleteEntrepriseById(id);
            return ResponseEntity.ok("Entreprise supprimée avec succès.");
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ AJAX Search with Pagination
    @GetMapping("/search")
    public ResponseEntity<Page<Entreprise>> searchEntreprises(
            @RequestParam String term,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Entreprise> results = entrepriseService.searchEntreprises(term, pageable);
        return ResponseEntity.ok(results);
    }
    @GetMapping("/recommend/{id}")
    public ResponseEntity<List<String>> getRecommendedEntreprises(@PathVariable Long id) {
        List<String> recommendedEntreprises = entrepriseService.recommendEntreprises(id);
        return ResponseEntity.ok(recommendedEntreprises);
    }
}
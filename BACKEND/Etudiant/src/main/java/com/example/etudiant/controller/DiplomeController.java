package com.example.etudiant.controller;

import com.example.etudiant.Services.IDiplomeService;
import com.example.etudiant.entities.Diplome;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/diplomes")
@CrossOrigin(origins = "*")
public class DiplomeController {

    private final IDiplomeService diplomeService;

    public DiplomeController(IDiplomeService diplomeService) {
        this.diplomeService = diplomeService;
    }

    // ✅ Ajouter un diplôme
    @PostMapping("/add")
    public Diplome ajouterDiplome(@RequestBody Diplome diplome) {
        return diplomeService.ajouterDiplome(diplome);
    }

    // ✅ Vérifier un diplôme par nom étudiant + université + type de diplôme
    @PostMapping("/verifier")
    public Map<String, Object> verifierDiplome(@RequestBody Map<String, String> request) {
        boolean estValide = diplomeService.verifierDiplome(
                request.get("nomEtudiant"),
                request.get("universite"),
                request.get("typeDiplome")
        );

        return Map.of(
                "valide", estValide,
                "message", estValide ? "Le diplôme est vérifié." : "Diplôme non trouvé."
        );
    }

    // ✅ Récupérer tous les diplômes vérifiés
    @GetMapping("/verifies")
    public List<Diplome> getDiplomesVerifies() {
        return diplomeService.getDiplomesVerifies();
    }
}

package com.example.etudiant.controller;

import com.example.etudiant.Services.IEtudiantService;
import com.example.etudiant.entities.Etudiant;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/etudiants")
@CrossOrigin(origins = "*") // Autoriser les requêtes depuis n'importe quelle origine
public class EtudiantController {

    private final IEtudiantService etudiantService;

    public EtudiantController(IEtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    @PostMapping("/add")
    public Etudiant ajouterEtudiant(@RequestBody Etudiant etudiant) {
        System.out.println("🔵 [LOG] Requête reçue pour ajouter un étudiant : " + etudiant);
        Etudiant savedEtudiant = etudiantService.ajouterEtudiant(etudiant);
        System.out.println("✅ [LOG] Étudiant ajouté : " + savedEtudiant);
        return savedEtudiant;
    }


    // ✅ Modifier un étudiant
    @PutMapping("/update")
    public Etudiant modifierEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantService.modifierEtudiant(etudiant);
    }

    // ✅ Supprimer un étudiant par ID
    @DeleteMapping("/delete/{id}")
    public void supprimerEtudiant(@PathVariable Long id) {
        etudiantService.supprimerEtudiant(id);
    }

    // ✅ Récupérer un étudiant par ID
    @GetMapping("/{id}")
    public Etudiant getEtudiantById(@PathVariable Long id) {
        return etudiantService.getEtudiantById(id);
    }

    // ✅ Récupérer tous les étudiants
    @GetMapping("/all")
    public List<Etudiant> getAllEtudiants() {
        return etudiantService.getAllEtudiants();
    }

    // ✅ Vérifier le diplôme d'un étudiant
    @PostMapping("/{etudiantId}/verifier-diplome")
    public Map<String, Object> verifierDiplome(@PathVariable Long etudiantId, @RequestBody Map<String, String> request) {
        boolean estValide = etudiantService.verifierDiplome(
                etudiantId,
                request.get("typeDiplome"),
                request.get("universite")
        );

        // ✅ Création d'une réponse structurée
        Map<String, Object> response = new HashMap<>();
        response.put("etudiantId", etudiantId);
        response.put("valide", estValide);
        response.put("message", estValide ? "Le diplôme est vérifié." : "Diplôme non trouvé.");

        return response;
    }
    // ✅ Endpoint pour récupérer les étudiants par spécialité
    @GetMapping("/specialite/{specialite}")
    public List<Etudiant> getEtudiantsParSpecialite(@PathVariable String specialite) {
        return etudiantService.getEtudiantsParSpecialite(specialite);
    }
}

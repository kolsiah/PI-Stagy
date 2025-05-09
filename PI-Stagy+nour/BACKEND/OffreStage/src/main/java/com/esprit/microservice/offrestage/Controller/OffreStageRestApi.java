package com.esprit.microservice.offrestage.Controller;

import com.esprit.microservice.offrestage.Client.EntrepriseClient;
import com.esprit.microservice.offrestage.Client.UserClient;
import com.esprit.microservice.offrestage.DTO.OffreStageDTO;
import com.esprit.microservice.offrestage.Entities.OffreStage;
import com.esprit.microservice.offrestage.Services.OffreStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/offres")
public class OffreStageRestApi {

    private final OffreStageService offreStageService;
    private final EntrepriseClient entrepriseClient;
    private final UserClient userClient;  // Injection du UserClient

    // 🔹 Ajouter une offre
   @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OffreStage> createOffre(@RequestBody OffreStage offreStage) {
        OffreStage saved = offreStageService.ajouterOffre(offreStage);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // 🔹 Récupérer toutes les offres
    @GetMapping
    public ResponseEntity<List<OffreStageDTO>> getAllOffres() {
        try {
            System.out.println("Getting all offres...");
            List<OffreStageDTO> offreStages = offreStageService.getAllOffres();
            System.out.println(offreStages);
            return new ResponseEntity<>(offreStages, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des offres : " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 🔹 Mettre à jour une offre
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OffreStage> updateOffre(@PathVariable Long id, @RequestBody OffreStage offreStage) {
        OffreStage updated = offreStageService.updateOffre(id, offreStage);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // 🔹 Supprimer une offre
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffre(@PathVariable Long id) {
        offreStageService.deleteOffre(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Recherche par mot-clé
    @GetMapping("/search/{param}")
    public ResponseEntity<List<OffreStage>> getSearch(@PathVariable String param) {
        return ResponseEntity.ok(offreStageService.getSearch(param));
    }

    // 🔹 Offres validées
    @GetMapping("/offreValider")
    public ResponseEntity<List<OffreStage>> getOffreValider() {
        return ResponseEntity.ok(offreStageService.getOffreByEtat());
    }

    // 🔹 Obtenir une offre avec détails
    @GetMapping("/{id}")
    public ResponseEntity<OffreStageDTO> getOffreById(@PathVariable Long id) {
        OffreStageDTO dto = offreStageService.getOffreById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    // 🔹 Obtenir les offres par utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OffreStageDTO>> getOffresByUser(@PathVariable Long userId) {
        // Cette méthode supplémentaire pourrait être implémentée pour filtrer les offres par utilisateur
        List<OffreStageDTO> offres = offreStageService.getAllOffres().stream()
                .filter(offre -> offre.getIdUser() != null && offre.getIdUser().equals(userId))
                .toList();
        return ResponseEntity.ok(offres);
    }
}
package com.example.entreprise.controllers;

import com.example.entreprise.entities.Stage;
import com.example.entreprise.services.IStageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/stages")
@AllArgsConstructor
@CrossOrigin("*")
public class StageController {

    @Autowired
    private IStageService iStageService;

    // Ajouter un stage
    @PostMapping("/addStage")
    public Stage addStage(@RequestBody Stage stage) {
        return iStageService.addStage(stage);
    }


    // Mettre à jour un stage
    @PutMapping("/updateStage")
    public Stage updateStage(@RequestBody Stage stage) {
        return iStageService.updateStage(stage);
    }

    // Récupérer tous les stages
    @GetMapping("/getAllStages")
    public List<Stage> retrieveAllStages() {
        return iStageService.retrieveAllStages();
    }

    // Récupérer un stage par ID
    @GetMapping("/findStageById/{idS}")
    public Stage retrieveStageById(@PathVariable int idS) {
        return iStageService.retrieveStageById(idS);
    }

    // Supprimer un stage par ID
    @DeleteMapping("/deleteStageById/{idS}")
    public void deleteStageById(@PathVariable int idS) {
        iStageService.deleteStageById(idS);
    }

    // Rechercher des stages selon leur type et état
    @GetMapping("/searchStagesByTypeAndState/{type}/{state}")
    public List<Stage> searchStagesByTypeAndState(
            @PathVariable String type,
            @PathVariable boolean state) {
        return iStageService.searchStagesByTypeAndState(type, state);
    }
}
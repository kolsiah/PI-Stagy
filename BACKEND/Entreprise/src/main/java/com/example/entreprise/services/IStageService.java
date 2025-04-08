package com.example.entreprise.services;

import com.example.entreprise.entities.Stage;
import java.util.Date;
import java.util.List;

public interface IStageService {

    // Ajouter un stage
    Stage addStage(Stage stage);

    // Mettre à jour un stage
    Stage updateStage(Stage stage);

    // Récupérer tous les stages
    List<Stage> retrieveAllStages();

    // Récupérer un stage par ID
    Stage retrieveStageById(int idS);

    // Supprimer un stage par ID
    void deleteStageById(int idS);

    // Rechercher des stages selon leur type et état
    List<Stage> searchStagesByTypeAndState(String type, boolean etat);
}
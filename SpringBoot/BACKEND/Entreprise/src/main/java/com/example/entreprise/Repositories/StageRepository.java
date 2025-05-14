package com.example.entreprise.Repositories;

import com.example.entreprise.entities.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StageRepository extends JpaRepository<Stage, Integer> {

    // Requête personnalisée pour rechercher des stages par type et état
    List<Stage> findByTypeAndEtat(String type, boolean etat);
}
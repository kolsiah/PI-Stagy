package com.example.entreprise.services;

import com.example.entreprise.entities.Entreprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

public interface IEntrepriseService {

    // Ajouter une entreprise
    Entreprise addEntreprise(Entreprise entreprise);

    // Mettre à jour une entreprise
    Entreprise updateEntreprise(Entreprise entreprise);

    // Récupérer toutes les entreprises
    List<Entreprise> retrieveAllEntreprises();

    // Récupérer une entreprise par ID
    Entreprise retrieveEntrepriseById(Long idE);

    // Supprimer une entreprise par ID
    void deleteEntrepriseById(Long idE);

    // Rechercher des entreprises selon leur secteur d'activité et date d'inscription
    List<Entreprise> searchEntreprisesBySecteurAndInscriptionDate(String secteur, LocalDate date);
    Page<Entreprise> searchEntreprises(String term, Pageable pageable);


    List<String> recommendEntreprises(Long entrepriseId);
}
package com.example.entreprise.Repositories;

import com.example.entreprise.entities.Entreprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {

    // Requête personnalisée pour rechercher des entreprises par secteur d'activité et date d'inscription
    List<Entreprise> findBySecteurActiviteAndDateInscription(String secteur, LocalDate date);
    Page<Entreprise> findByNomEntrepriseContainingIgnoreCaseOrVilleContainingIgnoreCase(String nom, String ville, Pageable pageable);
}
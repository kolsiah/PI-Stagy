package com.example.etudiant.repository;

import com.example.etudiant.entities.Diplome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiplomeRepository extends JpaRepository<Diplome, Long> {

    // ✅ Vérifier si un diplôme est vérifié en fonction du nom de l'étudiant et du type
    @Query("SELECT d FROM Diplome d WHERE d.etudiant.nom = :nom AND d.universite = :universite AND d.typeDiplome = :typeDiplome")
    Optional<Diplome> findByEtudiantNomAndUniversiteAndTypeDiplome(String nom, String universite, String typeDiplome);

    // ✅ Récupérer tous les diplômes d'un étudiant par son ID
    List<Diplome> findAllByEtudiantIdEtudiant(Long etudiantId);
}

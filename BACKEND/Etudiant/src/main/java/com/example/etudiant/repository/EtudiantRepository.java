package com.example.etudiant.repository;

import com.example.etudiant.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {  // Utiliser Long au lieu de Integer
    List<Etudiant> findByNom(String nom);

    // ✅ Trouver les étudiants par spécialité
    List<Etudiant> findBySpecialite(String specialite);}

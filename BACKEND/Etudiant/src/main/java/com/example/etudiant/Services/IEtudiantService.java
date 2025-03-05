package com.example.etudiant.Services;

import com.example.etudiant.entities.Etudiant;
import java.util.List;

public interface IEtudiantService {
    Etudiant ajouterEtudiant(Etudiant etudiant);
    Etudiant modifierEtudiant(Etudiant etudiant);
    void supprimerEtudiant(Long id);  // Changer int en Long
    Etudiant getEtudiantById(Long id);  // Changer int en Long
    List<Etudiant> getAllEtudiants();
    boolean verifierDiplome(Long etudiantId, String typeDiplome, String universite);
    // ✅ Ajouter la méthode pour récupérer les étudiants par spécialité
    List<Etudiant> getEtudiantsParSpecialite(String specialite);

    List<Etudiant> recommanderEtudiants(String specialite, int minDiplomes);
}

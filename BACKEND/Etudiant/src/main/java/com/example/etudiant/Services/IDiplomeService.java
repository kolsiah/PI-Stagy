package com.example.etudiant.Services;

import com.example.etudiant.entities.Diplome;
import java.util.List;

public interface IDiplomeService {
    Diplome ajouterDiplome(Diplome diplome);
    boolean verifierDiplome(String nomEtudiant, String universite, String typeDiplome);
    List<Diplome> getDiplomesVerifies();
    boolean verifierDiplomeParEtudiant(Long etudiantId, String typeDiplome, String universite);
}

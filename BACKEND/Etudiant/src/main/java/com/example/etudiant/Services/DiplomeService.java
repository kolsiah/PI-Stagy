package com.example.etudiant.Services;

import com.example.etudiant.entities.Diplome;
import com.example.etudiant.repository.DiplomeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiplomeService implements IDiplomeService {

    private final DiplomeRepository diplomeRepository;

    public DiplomeService(DiplomeRepository diplomeRepository) {
        this.diplomeRepository = diplomeRepository;
    }

    // Ajouter un diplôme
    @Transactional
    @Override
    public Diplome ajouterDiplome(Diplome diplome) {
        return diplomeRepository.save(diplome);
    }

    // Vérifier si un diplôme est validé
    @Override
    public boolean verifierDiplome(String nomEtudiant, String universite, String typeDiplome) {
        Optional<Diplome> diplome = diplomeRepository.findByEtudiantNomAndUniversiteAndTypeDiplome(nomEtudiant, universite, typeDiplome);
        return diplome.isPresent() && diplome.get().isVerifie();
    }

    // Récupérer tous les diplômes vérifiés
    @Override
    public List<Diplome> getDiplomesVerifies() {
        return diplomeRepository.findAll()
                .stream()
                .filter(Diplome::isVerifie)
                .collect(Collectors.toList());
    }

    // Vérification avancée d'un diplôme via l'ID de l'étudiant
    public boolean verifierDiplomeParEtudiant(Long etudiantId, String typeDiplome, String universite) {
        List<Diplome> diplomes = diplomeRepository.findAllByEtudiantIdEtudiant(etudiantId);
        return diplomes.stream()
                .anyMatch(d -> d.getTypeDiplome().equalsIgnoreCase(typeDiplome)
                        && d.getUniversite().equalsIgnoreCase(universite)
                        && d.isVerifie());
    }
}

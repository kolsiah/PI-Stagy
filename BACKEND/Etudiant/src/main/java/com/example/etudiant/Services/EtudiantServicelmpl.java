package com.example.etudiant.Services;

import com.example.etudiant.entities.Etudiant;
import com.example.etudiant.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantServicelmpl implements IEtudiantService {

    private final EtudiantRepository etudiantRepository;

    @Autowired
    public EtudiantServicelmpl(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    @Override
    public Etudiant ajouterEtudiant(Etudiant etudiant) {
        System.out.println("🟠 [LOG] Sauvegarde de l'étudiant : " + etudiant);
        Etudiant savedEtudiant = etudiantRepository.save(etudiant);
        System.out.println("✅ [LOG] Étudiant sauvegardé avec succès : " + savedEtudiant);
        return savedEtudiant;
    }


    @Override
    public Etudiant modifierEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    @Override
    public void supprimerEtudiant(Long id) {  // Utiliser Long
        etudiantRepository.deleteById(id);
    }

    @Override
    public Etudiant getEtudiantById(Long id) {  // Utiliser Long
        return etudiantRepository.findById((long) Math.toIntExact(id))
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé !"));
    }

    @Override
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }
    // service avancee de verification du diplome
    @Override
    public boolean verifierDiplome(Long etudiantId, String typeDiplome, String universite) {
        Optional<Etudiant> etudiant = etudiantRepository.findById(etudiantId);
        if (etudiant.isEmpty()) {
            throw new RuntimeException("Étudiant non trouvé !");
        }

        return etudiant.get().getDiplomes().stream()
                .anyMatch(d -> d.getTypeDiplome().equals(typeDiplome) && d.getUniversite().equals(universite) && d.isVerifie());
    }
    @Override
    public List<Etudiant> getEtudiantsParSpecialite(String specialite) {
        return etudiantRepository.findBySpecialite(specialite);
    }

}

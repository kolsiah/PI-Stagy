package com.esprit.microservice.offrestage.Services;

import com.esprit.microservice.offrestage.Client.EntrepriseClient;
import com.esprit.microservice.offrestage.Client.UserClient;
import com.esprit.microservice.offrestage.DTO.EntrepriseDTO;
import com.esprit.microservice.offrestage.DTO.OffreStageDTO;
import com.esprit.microservice.offrestage.DTO.UserDTO;
import com.esprit.microservice.offrestage.Entities.OffreStage;
import com.esprit.microservice.offrestage.Repositories.OffreStageRepository;

import com.example.entreprise.entities.Entreprise;
import feign.FeignException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OffreStageServiceImpl implements OffreStageService {

    private final OffreStageRepository offreStageRepository;
    private final EntrepriseClient entrepriseClient;  // Injection du FeignClient
    private final UserClient userClient;  // Injection du FeignClient pour User
    @Autowired
    private TwilioService twilioService;
    @Override
    public OffreStage ajouterOffre(OffreStage offreStage) {
        // Envoi d'un SMS après l'ajout de l'offre
        twilioService.sendSms("+216 56 061 271", "Une nouvelle offre de stage a été ajoutée: " + offreStage.getTitre());

        return offreStageRepository.save(offreStage);
    }

    @Override
    public List<OffreStageDTO> getAllOffres() {
        // Récupérer toutes les offres de stage
        List<OffreStage> offresStage = offreStageRepository.findAll();

        // Mapper chaque OffreStage en DTO avec l'entreprise et l'utilisateur associés
        return offresStage.stream().map(offreStage -> {
            OffreStageDTO dto = new OffreStageDTO(offreStage); // Mapper l'OffreStage en DTO

            try {
                // Appeler le FeignClient pour récupérer l'entreprise associée à l'offre
                Entreprise entreprise = entrepriseClient.getEntrepriseById(offreStage.getIdEntreprise());

                // Créer et remplir le DTO Entreprise
                EntrepriseDTO entrepriseDto = new EntrepriseDTO();
                entrepriseDto.setId(entreprise.getIdEntreprise());
                entrepriseDto.setNom_entreprise(entreprise.getNomEntreprise());
                entrepriseDto.setAdresse(entreprise.getAdresse());

                // Assigner l'entreprise au DTO de l'offre
                dto.setEntreprise(entrepriseDto);
            } catch (FeignException e) {
                // Si l'entreprise n'est pas trouvée, on assigne un DTO vide
                dto.setEntreprise(new EntrepriseDTO());
            }

            try {
                // Appeler le FeignClient pour récupérer l'utilisateur associé à l'offre
                if (offreStage.getIdUser() != null) {
                    UserDTO user = userClient.getUserById(offreStage.getIdUser());

                    // Assigner l'utilisateur au DTO de l'offre
                    dto.setUser(user);
                }
            } catch (FeignException e) {
                // Si l'utilisateur n'est pas trouvé, on assigne un DTO vide
                dto.setUser(new UserDTO());
            }

            return dto;
        }).collect(Collectors.toList()); // Collecter la liste de DTOs
    }


    @Override
    public OffreStageDTO getOffreById(Long id) {
        OffreStage offreStage = offreStageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offre de stage non trouvée"));

        OffreStageDTO dto = new OffreStageDTO(offreStage);

        try {
            Entreprise entreprise = entrepriseClient.getEntrepriseById(offreStage.getIdEntreprise());

            EntrepriseDTO entrepriseDto = new EntrepriseDTO();
            entrepriseDto.setId(entreprise.getIdEntreprise());
            entrepriseDto.setNom_entreprise(entreprise.getNomEntreprise());
            entrepriseDto.setAdresse(entreprise.getAdresse());

            dto.setEntreprise(entrepriseDto);
        } catch (FeignException e) {
            dto.setEntreprise(new EntrepriseDTO()); // fallback
        }

        try {
            // Récupérer les informations de l'utilisateur
            if (offreStage.getIdUser() != null) {
                UserDTO user = userClient.getUserById(offreStage.getIdUser());
                dto.setUser(user);
            }
        } catch (FeignException e) {
            dto.setUser(new UserDTO()); // fallback
        }

        return dto;
    }



    @Override
    public OffreStage updateOffre(Long id, OffreStage updatedOffre) {
        return offreStageRepository.findById(id)
                .map(offre -> {
                    offre.setTitre(updatedOffre.getTitre());
                    offre.setDescription(updatedOffre.getDescription());
                    offre.setIdEntreprise(updatedOffre.getIdEntreprise());
                    offre.setIdUser(updatedOffre.getIdUser());  // Mettre à jour l'ID de l'utilisateur
                    offre.setType(updatedOffre.getType());
                    offre.setDatePublication(updatedOffre.getDatePublication());
                    offre.setDebutExpiration(updatedOffre.getDebutExpiration());
                    offre.setEtat(updatedOffre.isEtat());
                    return offreStageRepository.save(offre);
                })
                .orElse(null);
    }

    @Override
    public String deleteOffre(Long id) {
        if (offreStageRepository.existsById(id)) {
            offreStageRepository.deleteById(id);
            return "Offre supprimée avec succès";
        }
        return "Offre non trouvée";
    }

    @Override
    public List<OffreStage> getSearch(String search) {
        return offreStageRepository.search(search);
    }

    @Override
    public List<OffreStage> getOffreByEtat() {
        return offreStageRepository.findByEtat(true);
    }

    // Méthode mise à jour pour récupérer l'offre avec les informations de l'entreprise et de l'utilisateur
    public OffreStageDTO getOffreWithEntrepriseAndUser(Long id) {
        Optional<OffreStage> offreStageOptional = offreStageRepository.findById(id);
        if (offreStageOptional.isPresent()) {
            OffreStage offreStage = offreStageOptional.get();

            // Créer le DTO
            OffreStageDTO offreStageDTO = new OffreStageDTO();
            offreStageDTO.setId(offreStage.getId());
            offreStageDTO.setTitre(offreStage.getTitre());
            offreStageDTO.setDescription(offreStage.getDescription());
            offreStageDTO.setType(offreStage.getType());
            offreStageDTO.setDatePublication(offreStage.getDatePublication());
            offreStageDTO.setDebutExpiration(offreStage.getDebutExpiration());
            offreStageDTO.setEtat(offreStage.isEtat());
            offreStageDTO.setIdEntreprise(offreStage.getIdEntreprise());
            offreStageDTO.setIdUser(offreStage.getIdUser());

            // Récupérer les informations de l'entreprise
            try {
                Entreprise entreprise = entrepriseClient.getEntrepriseById(offreStage.getIdEntreprise());
                EntrepriseDTO entrepriseDTO = new EntrepriseDTO();
                entrepriseDTO.setId(entreprise.getIdEntreprise());
                entrepriseDTO.setNom_entreprise(entreprise.getNomEntreprise());
                entrepriseDTO.setAdresse(entreprise.getAdresse());
                offreStageDTO.setEntreprise(entrepriseDTO);
            } catch (FeignException e) {
                offreStageDTO.setEntreprise(new EntrepriseDTO());
            }

            // Récupérer les informations de l'utilisateur
            try {
                if (offreStage.getIdUser() != null) {
                    UserDTO user = userClient.getUserById(offreStage.getIdUser());
                    offreStageDTO.setUser(user);
                }
            } catch (FeignException e) {
                offreStageDTO.setUser(new UserDTO());
            }

            return offreStageDTO;
        } else {
            return null;  // Retourner null si l'offre de stage n'existe pas
        }
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    @PostConstruct
    public void verifierOffresExpirees() {
        Date maintenant = new Date();
        List<OffreStage> offresExpirees = offreStageRepository.findByDebutExpirationBeforeAndEtatTrue(maintenant);

        for (OffreStage offre : offresExpirees) {
            offre.setEtat(false);  // Mise à jour de l'état
            offreStageRepository.save(offre);
        }

        System.out.println("✅ Vérification des offres expirées terminée. Offres mises à jour : " + offresExpirees.size());
    }
}
package com.esprit.microservice.offrestage.DTO;

import com.esprit.microservice.offrestage.Entities.OffreStage;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OffreStageDTO {
    private Long id;
    private String titre;
    private String description;
    private Long idEntreprise;
    private Long idUser;
    private String type;
    private Date datePublication;
    private Date debutExpiration;
    private boolean etat;

    private EntrepriseDTO entreprise;
    private UserDTO user;

    public OffreStageDTO(OffreStage offreStage) {
        this.id = offreStage.getId();
        this.titre = offreStage.getTitre();
        this.description = offreStage.getDescription();
        this.idEntreprise = offreStage.getIdEntreprise();
        this.idUser = offreStage.getIdUser();
        this.type = offreStage.getType();
        this.datePublication = offreStage.getDatePublication();
        this.debutExpiration = offreStage.getDebutExpiration();
        this.etat = offreStage.isEtat();

    }
}

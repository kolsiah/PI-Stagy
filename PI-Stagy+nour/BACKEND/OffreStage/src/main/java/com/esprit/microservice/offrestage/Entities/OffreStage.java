package com.esprit.microservice.offrestage.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OffreStage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    public String titre;
    public String description;
    public Long idEntreprise;
    public Long idUser;
    public String type;
    public Date datePublication;
    public Date debutExpiration;
    public boolean etat ;

    @OneToMany(mappedBy = "offreStage")
    @JsonIgnore
    private List<Candidature> candidatures;
}

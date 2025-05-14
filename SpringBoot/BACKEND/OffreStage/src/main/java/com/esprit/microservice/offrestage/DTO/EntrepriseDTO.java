package com.esprit.microservice.offrestage.DTO;

import lombok.*;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
public class EntrepriseDTO {
    private Long id;
    private String nom_entreprise;
    private String adresse;
}

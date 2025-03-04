package com.example.etudiant.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Diplome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDiplome;

    private String typeDiplome;
    private String universite;
    private boolean verifie;

    @ManyToOne
    @JoinColumn(name = "id_etudiant", nullable = false)
    @ToString.Exclude  // Évite les boucles infinies
    private Etudiant etudiant;
}
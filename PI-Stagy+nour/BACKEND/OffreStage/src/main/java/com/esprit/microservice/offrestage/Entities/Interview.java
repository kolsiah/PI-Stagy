package com.esprit.microservice.offrestage.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidature_id", nullable = false)
    private Candidature candidature;

    private LocalDateTime dateEntretien;

    private String mode; // Exemple : En ligne, Présentiel, Téléphonique

    private String lienVisio; // Lien Zoom, Meet, etc.

    private Double note; // Note de performance

    private String commentaire; // Remarques générales

    private String status; // Exemple : Prévu, Terminé, Annulé
}

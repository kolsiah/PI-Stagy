    package com.esprit.microservice.offrestage.Entities;

    import com.esprit.microservice.offrestage.DTO.UserDTO;
    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import java.time.LocalDateTime;

    @Entity
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name = "candidatures")
    public class Candidature {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;  // Identifiant unique de la candidature

        Long userId; // Stocke uniquement l'ID de l'utilisateur (microservice User)
        // ID de l'étudiant
        @ManyToOne
        @JoinColumn(name = "offre_stage_id", nullable = false)
        private  OffreStage offreStage;  // ID de l'offre de stage

        @Column(name = "etat", length = 20, nullable = false)
        private String etat;  // En attente, acceptée, refusée

        @Column(name = "cv_path", nullable = false)
        private String cvPath;  // Chemin du CV stocké

        @Column(name = "lettre_motivation_path", nullable = false)
        private String lettreMotivationPath;  // Chemin de la lettre de motivation stockée

        @Column(name = "date_postulation", nullable = false)
        private LocalDateTime datePostulation;  // Date de la candidature
        @Transient
        @JsonIgnore
        UserDTO user; // Récupéré via FeignClient
    }

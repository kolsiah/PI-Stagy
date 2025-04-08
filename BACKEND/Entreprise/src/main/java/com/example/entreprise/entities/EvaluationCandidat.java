package com.example.entreprise.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "evaluation_candidat")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "stage")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EvaluationCandidat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idEvaluationCandidat;

    Long studentID;

    int rating;

@Enumerated(EnumType.STRING)
    CompetenceLevel competenceLevel;

    @Column(length = 1000)
    String comment;

    boolean isRecommended;

    String jobOpportunity;

    @Temporal(TemporalType.TIMESTAMP)
    Date dateEvaluation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id")
    Stage stage;

    public enum CompetenceLevel {
        DEBUTANT, INTERMEDIAIRE, EXPERT
    }
}
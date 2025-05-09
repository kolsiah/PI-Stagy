package com.example.entreprise.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Table(name = "stage")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"entreprise", "evaluationCandidat"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String titre;

    @Column(length = 1000)
    String description;

    String type;

    @Temporal(TemporalType.DATE)
    Date datePublication;

    @Temporal(TemporalType.DATE)
    Date debutExpiration;

    boolean etat;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "entreprise_id")
    //@JsonBackReference
    //Entreprise entreprise;

    @OneToOne(mappedBy = "stage", cascade = CascadeType.ALL, orphanRemoval = true)
    EvaluationCandidat evaluationCandidat;
}
package com.example.etudiant.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "etudiant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEtudiant;

    private String nom;
    private String prenom;
    private String dateNaissance;
    private String tel;
    private String mail;
    private String classe;

    @Column(nullable = false)
    private String specialite;

    private String skills;
    private String password;

    // Relation avec Diplome
    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Diplome> diplomes;


}

package com.example.entreprise.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entreprise")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "ratings") // ✅ Prevent infinite loops in JSON serialization
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entreprise")
    Long idEntreprise;

    @Column(name = "nom_entreprise")
    String nomEntreprise;

    @Column(name = "adresse")
    String adresse;

    @Column(name = "ville")
    String ville;

    @Column(name = "secteur_activite")
    String secteurActivite;

    @Column(name = "email_contact")
    String emailContact;

    @Column(name = "date_inscription")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate dateInscription;

    @OneToMany(mappedBy = "entreprise", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference  // ✅ Prevents infinite recursion issue
    private List<Rating> ratings;

    @Column(nullable = false)
    private double moyenneNote = 0.0;  // ✅ Stored in DB

    // ✅ Recalculate moyenneNote when ratings change
    public void updateMoyenneNote() {
        if (ratings == null || ratings.isEmpty()) {
            this.moyenneNote = 0.0;
        } else {
            this.moyenneNote = ratings.stream().mapToDouble(Rating::getNote).average().orElse(0.0);
        }
    }
}

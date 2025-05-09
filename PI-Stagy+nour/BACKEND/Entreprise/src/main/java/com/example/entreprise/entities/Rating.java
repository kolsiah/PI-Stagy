package com.example.entreprise.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)  // ✅ Prevents errors when unknown fields exist
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "entreprise_id", nullable = false)
    @JsonBackReference  // ✅ Prevents infinite recursion issue
    private Entreprise entreprise;

    @Column(nullable = false)
    private int note;  // ✅ Rating from 1 to 5

    @Column(length = 500)
    private String commentaire;  // ✅ Optional comment

    @Column(name = "date_created", updatable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();  // ✅ Timestamp

    public Rating() {}

    public Rating(Entreprise entreprise, int note, String commentaire) {
        this.entreprise = entreprise;
        this.note = note;
        this.commentaire = commentaire;
        this.dateCreated = LocalDateTime.now();
    }
}

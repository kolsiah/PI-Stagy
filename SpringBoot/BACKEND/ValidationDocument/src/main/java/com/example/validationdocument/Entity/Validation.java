package com.example.validationdocument.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
public class Validation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idValidation;

    private int idDocument;
    private int idEncadrant;
    private int etudiantId;  // ✅ nouveau champ
    private int offreId;     // ✅ nouveau champ

    private Date dateValidation;

    @Enumerated(EnumType.STRING)
    private StatutV statut;

    @Column(columnDefinition = "TEXT")
    private String commentaire;


    public Validation(int idValidation, int idDocument, int idEncadrant,int offreId,int etudiantId, Date dateValidation, StatutV statut, String commentaire) {
        this.idValidation = idValidation;
        this.idDocument = idDocument;
        this.idEncadrant = idEncadrant;
        this.offreId = offreId;
        this.etudiantId = etudiantId;
        this.dateValidation = dateValidation;
        this.statut = statut;
        this.commentaire = commentaire;
    }

    public Validation() {
    }
    public int getEtudiantId(){return etudiantId;}

    public int getOffreId(){return offreId;}

    public int getIdValidation() {
        return idValidation;
    }

    public void setIdValidation(int idValidation) {
        this.idValidation = idValidation;
    }

    public int getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
    }

    public int getIdEncadrant() {
        return idEncadrant;
    }

    public void setIdEncadrant(int idEncadrant) {
        this.idEncadrant = idEncadrant;
    }

    public void setEtudiantId(int etudiantId) {this.etudiantId = etudiantId;}

    public void setOffreId(int offreId) {this.offreId = offreId;}

    public Date getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(Date dateValidation) {
        this.dateValidation = dateValidation;
    }

    public StatutV getStatut() {
        return statut;
    }

    public void setStatut(StatutV statut) {
        this.statut = statut;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}

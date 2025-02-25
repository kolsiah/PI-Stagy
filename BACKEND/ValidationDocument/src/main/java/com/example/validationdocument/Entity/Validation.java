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
    private Date dateValidation;

    @Enumerated(EnumType.STRING)
    private StatutV statut;

    private String commentaire;

    public Validation(int idValidation, int idDocument, int idEncadrant, Date dateValidation, StatutV statut, String commentaire) {
        this.idValidation = idValidation;
        this.idDocument = idDocument;
        this.idEncadrant = idEncadrant;
        this.dateValidation = dateValidation;
        this.statut = statut;
        this.commentaire = commentaire;
    }

    public Validation() {
    }

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

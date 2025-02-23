package com.example.document.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
public class Document implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDocument;

    private String titreDocument;
    private String urlDocument;

    @Enumerated(EnumType.STRING)
    private Type typeDocument;

    @Enumerated(EnumType.STRING)
    private Statut statutDocument;

    @Temporal(TemporalType.DATE)
    private Date dateUpload;

    private int etudiantId;
    private int stageId;

    public int getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
    }

    public String getTitreDocument() {
        return titreDocument;
    }

    public void setTitreDocument(String titreDocument) {
        this.titreDocument = titreDocument;
    }

    public String getUrlDocument() {
        return urlDocument;
    }

    public void setUrlDocument(String urlDocument) {
        this.urlDocument = urlDocument;
    }

    public Type getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(Type typeDocument) {
        this.typeDocument = typeDocument;
    }

    public Statut getStatutDocument() {
        return statutDocument;
    }

    public void setStatutDocument(Statut statutDocument) {
        this.statutDocument = statutDocument;
    }

    public Date getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(Date dateUpload) {
        this.dateUpload = dateUpload;
    }

    public int getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(int etudiantId) {
        this.etudiantId = etudiantId;
    }

    public int getStageId() {
        return stageId;
    }

    public void setStageId(int stageId) {
        this.stageId = stageId;
    }

    public Document() {
    }

    public Document(int idDocument, String titreDocument, String urlDocument, Type typeDocument, Statut statutDocument, Date dateUpload, int etudiantId, int stageId) {
        this.idDocument = idDocument;
        this.titreDocument = titreDocument;
        this.urlDocument = urlDocument;
        this.typeDocument = typeDocument;
        this.statutDocument = statutDocument;
        this.dateUpload = dateUpload;
        this.etudiantId = etudiantId;
        this.stageId = stageId;
    }

    @Override
    public String toString() {
        return "Document{" +
                "idDocument=" + idDocument +
                ", titreDocument='" + titreDocument + '\'' +
                ", urlDocument='" + urlDocument + '\'' +
                ", typeDocument=" + typeDocument +
                ", statutDocument=" + statutDocument +
                ", dateUpload=" + dateUpload +
                ", etudiantId=" + etudiantId +
                ", stageId=" + stageId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return idDocument == document.idDocument && etudiantId == document.etudiantId && stageId == document.stageId && Objects.equals(titreDocument, document.titreDocument) && Objects.equals(urlDocument, document.urlDocument) && typeDocument == document.typeDocument && statutDocument == document.statutDocument && Objects.equals(dateUpload, document.dateUpload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDocument, titreDocument, urlDocument, typeDocument, statutDocument, dateUpload, etudiantId, stageId);
    }
}

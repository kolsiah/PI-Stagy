package com.example.validationdocument.dto;

public class ValidationEvent {
    private int documentId;
    private String status; // "APPROVED" or "REJECTED"
    private String commentaire;

    public ValidationEvent(int documentId, String status, String commentaire) {
        this.documentId = documentId;
        this.status = status;
        this.commentaire = commentaire;
    }

    public ValidationEvent() {
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return "ValidationEvent{" +
                "documentId=" + documentId +
                ", status='" + status + '\'' +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}

package com.example.validationdocument.Services;

import com.example.validationdocument.Entity.Validation;

public interface IValidationDoc {
    public Validation validateDocument(int documentId, int encadrantId, boolean isApproved, String commentaire);
}

package com.example.validationdocument.Services;

import com.example.validationdocument.Entity.StatutV;
import com.example.validationdocument.Entity.Validation;

import java.util.List;

public interface IValidationDoc {
    public void validateDocument(int idDocument, int idEncadrant, StatutV statutV, String commentaire);
    public List<Validation> getValidations();
    public Validation getValidation(int idValidation);
    public String deleteValidation(int idValidation);
    public Validation updateValidation(int idValidation, Validation validation);
}

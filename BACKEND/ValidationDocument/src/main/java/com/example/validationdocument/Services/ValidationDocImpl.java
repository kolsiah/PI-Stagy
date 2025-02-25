package com.example.validationdocument.Services;

import com.example.validationdocument.Entity.StatutV;
import com.example.validationdocument.Entity.Validation;
import com.example.validationdocument.Repository.ValidationRepository;
import com.example.validationdocument.dto.ValidationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class ValidationDocImpl implements IValidationDoc{
    @Autowired
    ValidationRepository validationRepository;
    @Autowired
    KafkaTemplate<String, ValidationEvent> kafkaTemplate;

    @Override
    public Validation validateDocument(int documentId, int encadrantId, boolean isApproved, String commentaire) {
        Validation validation = new Validation();
        validation.setIdDocument(documentId);
        validation.setIdEncadrant(encadrantId);
        validation.setDateValidation(new Date());
        validation.setStatut(isApproved ? StatutV.APPROVED : StatutV.REJECTED);
        validation.setCommentaire(commentaire);
        Validation savedValidation = validationRepository.save(validation);
        ValidationEvent event = new ValidationEvent(documentId, savedValidation.getStatut().name(), commentaire);
        kafkaTemplate.send("validation-events", event);
        return savedValidation;
    }
}

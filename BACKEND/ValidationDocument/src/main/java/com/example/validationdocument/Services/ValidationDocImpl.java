package com.example.validationdocument.Services;

import com.example.validationdocument.Entity.StatutV;
import com.example.validationdocument.Entity.Validation;
import com.example.validationdocument.Repository.ValidationRepository;
//import com.example.validationdocument.dto.ValidationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ValidationDocImpl implements IValidationDoc{
    @Autowired
    ValidationRepository validationRepository;
        //KafkaTemplate<String, ValidationEvent> kafkaTemplate;
    @Autowired
        RestTemplate restTemplate;
    private final String documentServiceUrl = "http://localhost:8087/documents";
    @Override
    public void validateDocument(int idDocument,int idEncadrant,StatutV statutV,String commentaire) {
        Validation val = new Validation();
        val.setIdDocument(idDocument);
        val.setDateValidation(new Date());
        val.setCommentaire(commentaire);
        val.setStatut(statutV);
        val.setIdEncadrant(idEncadrant);
        validationRepository.save(val);


        // Construct API URL for Document Microservice
        String updateUrl = documentServiceUrl + "/" + idDocument + "/status?status=" + statutV;

        // Call the Document Microservice to update the document status
        try {
            restTemplate.put(updateUrl, null);
            System.out.println("Document status updated successfully for document ID: " + idDocument);
        } catch (Exception e) {
            System.err.println("Error updating document status: " + e.getMessage());
        }
    }

    @Override
    public List<Validation> getValidations() {
        return validationRepository.findAll();
    }

    @Override
    public Validation getValidation(int idValidation) {
        return validationRepository.findById(idValidation).orElse(null);
    }

    @Override
    public String deleteValidation(int idValidation) {
        if (validationRepository.findById(idValidation).isPresent()) {
            validationRepository.deleteById(idValidation);
        }
        return "Validation Introuvable";
    }

    @Override
    public Validation updateValidation(int idValidation, Validation validation) {
        Optional<Validation> val = validationRepository.findById(idValidation);
        if (val.isPresent()) {
            Validation valToUpdate = val.get();
            valToUpdate.setIdValidation(idValidation);
            valToUpdate.setDateValidation(validation.getDateValidation());
            valToUpdate.setCommentaire(validation.getCommentaire());
            valToUpdate.setStatut(validation.getStatut());
            valToUpdate.setIdEncadrant(validation.getIdEncadrant());
            return validationRepository.save(valToUpdate);
        }
        return null;
    }

}

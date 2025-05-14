package com.example.validationdocument.Services;

import com.example.validationdocument.Entity.StatutV;
import com.example.validationdocument.Entity.Validation;
import com.example.validationdocument.Repository.ValidationRepository;
//import com.example.validationdocument.dto.ValidationEvent;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private DocumentClient documentClient;
    @Autowired
    private EmailService emailService;
    @Override
    public void validateDocument(int idDocument, int idEncadrant, StatutV statutV, String commentaire, int etudiantId, int offreId) {
        Validation val = new Validation();
        val.setIdDocument(idDocument);
        val.setDateValidation(new Date());
        val.setCommentaire(commentaire);
        val.setStatut(statutV);
        val.setIdEncadrant(idEncadrant);
        val.setEtudiantId(etudiantId);  // ✅ ajouté
        val.setOffreId(offreId);        // ✅ ajouté
        validationRepository.save(val);

        try {
            documentClient.updateDocumentStatus(idDocument, statutV);
            String email = "adam.hachana@esprit.tn";
            String subject = "📝 Validation du document";
            String msg = String.format("Le document ID %d a été %s.\nCommentaire : %s",
                    idDocument, statutV.toString().toLowerCase(), commentaire);
            emailService.sendValidationEmail(email, subject, msg);
        } catch (Exception e) {
            System.err.println("❌ Error updating document status via Feign: " + e.getMessage());
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
            return "validation deleted successfully";
        }
        return "Validation Introuvable";
    }
    @Override
    public List<Validation> getValidationsByIdEtud(int id){
        return validationRepository.findValidationByEtudiantId(id);
    }
    @Override
    public List<Validation> getValidationsByIdEncadrant(int id){
        return validationRepository.findValidationByIdEncadrant(id);
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
            valToUpdate.setEtudiantId(validation.getEtudiantId());   // ✅ ajouté
            valToUpdate.setOffreId(validation.getOffreId());         // ✅ ajouté
            return validationRepository.save(valToUpdate);
        }
        return null;
    }

}

package com.esprit.microservice.offrestage.Client;

import com.esprit.microservice.offrestage.DTO.OffreStageDTO;

import com.example.entreprise.entities.Entreprise;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "entreprise-service")  // Le nom du service dans Eureka
public interface EntrepriseClient {

    @GetMapping("/entreprises/retrieveEntrepriseById/{id}")  // L'endpoint pour récupérer l'entreprise par son ID
    Entreprise getEntrepriseById(@PathVariable("id") Long id);
}

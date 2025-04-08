package com.example.entreprise.services;

import com.example.entreprise.entities.Entreprise;
import com.example.entreprise.Repositories.EntrepriseRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntrepriseService implements IEntrepriseService { // ✅ Implémentation correcte


    private final EntrepriseRepository entrepriseRepository;

    // Constructor-based injection explicitly, but no Lombok annotations
    @Autowired
    public EntrepriseService(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    @Override
    public Entreprise addEntreprise(Entreprise entreprise) {
        return entrepriseRepository.save(entreprise);
    }

    @Override
    public Entreprise updateEntreprise(Entreprise entreprise) {
        return entrepriseRepository.save(entreprise);
    }

    @Override
    public List<Entreprise> retrieveAllEntreprises() {
        return entrepriseRepository.findAll();
    }

    @Override
    public Entreprise retrieveEntrepriseById(Long idE) {
        return entrepriseRepository.findById(idE).orElse(null);
    }

    @Override
    public void deleteEntrepriseById(Long idE) {
        entrepriseRepository.deleteById(idE);
    }

    @Override
    public List<Entreprise> searchEntreprisesBySecteurAndInscriptionDate(String secteur, LocalDate date) {
        return entrepriseRepository.findBySecteurActiviteAndDateInscription(secteur, date);
    }
    @Override
    public Page<Entreprise> searchEntreprises(String term, Pageable pageable) {
        return entrepriseRepository.findByNomEntrepriseContainingIgnoreCaseOrVilleContainingIgnoreCase(term, term, pageable);
    }

    @Override
    public List<String> recommendEntreprises(Long entrepriseId) {
        List<String> recommendations = new ArrayList<>();
        try {

            String pythonPath = "C:\\Users\\HP\\AppData\\Local\\Programs\\Python\\Python312\\python.exe";
            String scriptPath = "C:\\Users\\HP\\Desktop\\PI-Stagy\\BACKEND\\python\\recommendation.py";

            ProcessBuilder processBuilder = new ProcessBuilder(
                    pythonPath, scriptPath, String.valueOf(entrepriseId)
            );

            // Capture Python errors as well
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Python Output: " + line); // Debugging
                recommendations.add(line.trim());
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Python script exited with error code: " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recommendations;
    }

}
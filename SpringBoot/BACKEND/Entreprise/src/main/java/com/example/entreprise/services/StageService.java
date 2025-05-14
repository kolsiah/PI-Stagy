package com.example.entreprise.services;

import com.example.entreprise.entities.Stage;
import com.example.entreprise.Repositories.StageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StageService implements IStageService {

    @Autowired
    private StageRepository stageRepository;

    @Override
    public Stage addStage(Stage stage) {
        return stageRepository.save(stage);
    }

    @Override
    public Stage updateStage(Stage stage) {
        return stageRepository.save(stage);
    }

    @Override
    public List<Stage> retrieveAllStages() {
        return stageRepository.findAll();
    }

    @Override
    public Stage retrieveStageById(int idS) {
        return stageRepository.findById(idS).orElse(null);
    }

    @Override
    public void deleteStageById(int idS) {
        stageRepository.deleteById(idS);
    }

    @Override
    public List<Stage> searchStagesByTypeAndState(String type, boolean etat) {
        return stageRepository.findByTypeAndEtat(type, etat);
    }
}
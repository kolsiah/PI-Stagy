package com.esprit.microservice.offrestage.Services;

import com.esprit.microservice.offrestage.DTO.OffreStageDTO;
import com.esprit.microservice.offrestage.Entities.OffreStage;
import java.util.List;
import java.util.Optional;

public interface OffreStageService {
    OffreStage ajouterOffre(OffreStage offreStage);
    List<OffreStageDTO> getAllOffres();
    OffreStageDTO getOffreById(Long id);
    OffreStage updateOffre(Long id, OffreStage updatedOffre);
    String deleteOffre(Long id);
    List<OffreStage> getSearch(String search);
    List<OffreStage> getOffreByEtat();
}

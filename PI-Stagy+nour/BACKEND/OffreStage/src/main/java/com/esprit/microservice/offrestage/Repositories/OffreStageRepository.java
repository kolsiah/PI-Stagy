package com.esprit.microservice.offrestage.Repositories;

import com.esprit.microservice.offrestage.Entities.OffreStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OffreStageRepository extends JpaRepository<OffreStage , Long> {

    @Query(value = "SELECT * FROM offre_stage " +
            "WHERE Titre LIKE CONCAT('%', :motRecherche, '%') " +
            "   OR Description LIKE CONCAT('%', :motRecherche, '%') " +
            "   OR Entreprise LIKE CONCAT('%', :motRecherche, '%')", nativeQuery = true)
    List<OffreStage> search(@Param("motRecherche") String motRecherche);

    List<OffreStage> findByEtat(boolean etat);
    List<OffreStage> findByDebutExpirationBeforeAndEtatTrue(Date date);
}

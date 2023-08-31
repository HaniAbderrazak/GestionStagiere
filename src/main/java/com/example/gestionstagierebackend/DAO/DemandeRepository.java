package com.example.gestionstagierebackend.DAO;

import com.example.gestionstagierebackend.Entities.Demande;
import com.example.gestionstagierebackend.Entities.Stagiere;
import com.example.gestionstagierebackend.Entities.offreDeStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DemandeRepository  extends JpaRepository<Demande, Integer> {
    List<Demande> findByOffreDeStage(offreDeStage offre);
    Optional<Demande> findByOffreDeStageIdAndStagiereId(int offreDeStageId, int stagiereId);

    List<Demande> findDemandesByOffreDeStageAndStagiere(offreDeStage offre, Stagiere s);

    List<Demande> findByOffreDeStageIdAndEtat(int offerId, String accepter);

    List<Demande> findByStagiereId( int stagiereId);
    List<Demande> findDemandesByOffreDeStageId( int offreId);
    List<Demande> findByStagiereIdAndEtat(int stagiere,String Etat);

}

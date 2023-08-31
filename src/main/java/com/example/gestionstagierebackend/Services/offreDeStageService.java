package com.example.gestionstagierebackend.Services;

import com.example.gestionstagierebackend.DAO.DemandeRepository;
import com.example.gestionstagierebackend.DAO.offreDeStageRepository;
import com.example.gestionstagierebackend.Entities.Demande;
import com.example.gestionstagierebackend.Entities.offreDeStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class offreDeStageService {
    @Autowired
    private offreDeStageRepository offreRepository ;
    @Autowired
    private DemandeRepository demandeRepository ;


    public List<offreDeStage> getAllOffres()
    {
        return offreRepository.findAll();
    }

    public offreDeStage getOffreById( int id )
    {
        return offreRepository.findById(id).get();
    }

    public offreDeStage addOffre( offreDeStage offre )
    {
        return offreRepository.save(offre);
    }

    public void deleteOffre(int id) {
        List<Demande> list=demandeRepository.findDemandesByOffreDeStageId(id);
        for (Demande item:list) {
            demandeRepository.delete(item);
        }
        offreRepository.deleteById(id);

    }


    public ResponseEntity<offreDeStage> updateOffre(int id,offreDeStage offreUpdated ) {
        Optional<offreDeStage> optionalOffreDeStage = offreRepository.findById(id);
        if (optionalOffreDeStage.isPresent()) {
            offreDeStage existingOffre = optionalOffreDeStage.get();
            existingOffre.setTitre(offreUpdated.getTitre());
            existingOffre.setDescription(offreUpdated.getDescription());
            offreDeStage savedProduct = offreRepository.save(existingOffre);
            return ResponseEntity.ok(savedProduct);
        } else {
                return ResponseEntity.notFound().build();
            }


    }
}

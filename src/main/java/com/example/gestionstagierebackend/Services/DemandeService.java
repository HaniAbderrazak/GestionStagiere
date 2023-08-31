package com.example.gestionstagierebackend.Services;

import com.example.gestionstagierebackend.DAO.DemandeRepository;

import com.example.gestionstagierebackend.DAO.offreDeStageRepository;
import com.example.gestionstagierebackend.DAO.stagiereRepository;
import com.example.gestionstagierebackend.Entities.Demande;

import com.example.gestionstagierebackend.Entities.Stagiere;
import com.example.gestionstagierebackend.Entities.offreDeStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DemandeService {
    @Autowired
    private DemandeRepository DemandeRep;
    @Autowired
    private offreDeStageRepository offreRep;
    @Autowired
    private stagiereRepository stageRep;



    public List<Demande> getAllDemande() {
        return DemandeRep.findAll();
    }

    public Demande getDemandeById(int id) {
        return DemandeRep.findById(id).get();
    }



    public Demande AddDem(Demande d) {
        int stagiereId=d.getStagiere().getId();
        int offreId=d.getOffreDeStage().getId();
        Optional<offreDeStage> offre=offreRep.findById(offreId);
        Optional<Stagiere> stagiere=stageRep.findById(stagiereId);
        Optional<Demande> demande = DemandeRep.findByOffreDeStageIdAndStagiereId(offreId,stagiereId );
        if (demande.isPresent()) {
            return null;
        } else {

             d.setStagiere(stagiere.get());
             d.setOffreDeStage(offre.get());
             d.setEtat("attente");
            DemandeRep.save(d);
            return d;
        }

    }


    public void deleteDemande(int id) {
        DemandeRep.deleteById(id);
        System.out.println("delete");
    }

    public void acceptStagiereOnOffre(int idOffre, int idStagiere) {
        // Assuming you have a method to find the demande by idOffre and idStagiere
        Optional<Demande> optionalDemande = DemandeRep.findByOffreDeStageIdAndStagiereId(idOffre, idStagiere);

        if (optionalDemande.isPresent()) {
            Demande existingDemande = optionalDemande.get();
            existingDemande.setEtat("accepter");
            DemandeRep.save(existingDemande);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Demande not found for the provided offre and stagiere.");
        }
    }


    public ResponseEntity<Demande> updateDemande(int id, Demande demandeUpdated) {
        Optional<Demande> optionalDemande = DemandeRep.findById(id);
        if (optionalDemande.isPresent()) {
            Demande existingDemande = optionalDemande.get();

            // Update attributes of existingDemande with values from demandeUpdated
            existingDemande.setStagiere(demandeUpdated.getStagiere());
            existingDemande.setOffreDeStage(demandeUpdated.getOffreDeStage());
            existingDemande.setEtat(demandeUpdated.getEtat());

            Demande savedDemande = DemandeRep.save(existingDemande);
            return ResponseEntity.ok(savedDemande);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public List<Stagiere> getAcceptedStagieresForOffer(int offerId) {
        List<Demande> acceptedDemandes = DemandeRep.findByOffreDeStageIdAndEtat(offerId, "accepter");

        List<Stagiere> acceptedStagieres = acceptedDemandes.stream()
                .map(Demande::getStagiere)
                .collect(Collectors.toList());

        return acceptedStagieres;
    }

   public Demande getDemandeByIdOffreAndStagiereId(int idOffre, int idStagiere) {
        Optional<Demande>demande=DemandeRep.findByOffreDeStageIdAndStagiereId(idOffre, idStagiere);
        if(demande.isPresent())
            return demande.get() ;

        else return null;


    }
    public List<Demande> getDemandesByStagiereId(int IdStagiere){
        List<Demande> demandesOfStagiere = DemandeRep.findByStagiereId(IdStagiere);
        return demandesOfStagiere;
    }
    public List<Demande> getDemandesByStagiereIdAndEtat(int IdStagiere){
        List<Demande> demandesOfStagiere = DemandeRep.findByStagiereIdAndEtat(IdStagiere,"accepter");
        return demandesOfStagiere;
    }

}
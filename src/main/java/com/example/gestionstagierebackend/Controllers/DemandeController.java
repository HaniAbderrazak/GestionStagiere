package com.example.gestionstagierebackend.Controllers;

import com.example.gestionstagierebackend.Entities.Demande;
import com.example.gestionstagierebackend.Entities.Stagiere;

import com.example.gestionstagierebackend.Services.DemandeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/demandes")

public class DemandeController {
    @Autowired
    private DemandeService demandeService;

    @GetMapping()
    public List<Demande> getAllDemandes() {
        return demandeService.getAllDemande();
    }

    @GetMapping("/{id}")
    public Demande getDemandeById(@PathVariable int id) {
        return demandeService.getDemandeById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Object> addDemande(@RequestBody Demande demande) {
        Demande DEM = demandeService.AddDem(demande);

        if (DEM != null) {
            return ResponseEntity.ok(DEM);
        } else {
            return ResponseEntity.status(400).body(" already exists for the provided stagiere and offre");
        }
    }
   // @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/{id}")
    public void modifierDemande(@PathVariable int id, @RequestBody Demande demande) {
        demandeService.updateDemande(id, demande);
    }
   // @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteDemande(@PathVariable int id) {
        demandeService.deleteDemande(id);
    }

    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/acceptStagiere/{idOffre}/{idStagiere}")
    public void acceptStagiere(
            @PathVariable int idOffre,
            @PathVariable int idStagiere
    ) {
        demandeService.acceptStagiereOnOffre(idOffre, idStagiere);
      }

    @GetMapping("/Demande/{idOffre}/{idStagiere}")
    public Demande getDemandeWithOffreAndStage(
            @PathVariable int idOffre,
            @PathVariable int idStagiere
    ) {
        Demande DEM=demandeService.getDemandeByIdOffreAndStagiereId(idOffre, idStagiere);
        if(DEM!=null){
            return DEM;
        }
        else {
            return null;
        }
    }
   // @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/accepted/{offerId}")
    public ResponseEntity<List<Stagiere>> getAcceptedStagieresForOffer(@PathVariable int offerId) {
        List<Stagiere> acceptedStagieres = demandeService.getAcceptedStagieresForOffer(offerId);
        return ResponseEntity.ok(acceptedStagieres);
    }
    @GetMapping("/stagebystagiere/{stagiereId}")
    public ResponseEntity<List<Demande>> getDemandeByStagiere(@PathVariable int stagiereId) {
        List<Demande> acceptedStagieres = demandeService.getDemandesByStagiereId(stagiereId);
        return ResponseEntity.ok(acceptedStagieres);
    }
    @GetMapping("/stageAccepted/{stagiereId}")
    public ResponseEntity<List<Demande>> getAcceptedStage(@PathVariable int stagiereId) {
        List<Demande> acceptedStagieres = demandeService.getDemandesByStagiereIdAndEtat(stagiereId);
        return ResponseEntity.ok(acceptedStagieres);
    }
}

package com.example.gestionstagierebackend.Controllers;
import com.example.gestionstagierebackend.DAO.offreDeStageRepository;
import com.example.gestionstagierebackend.Entities.Stagiere;
import com.example.gestionstagierebackend.Entities.offreDeStage;
import com.example.gestionstagierebackend.Services.offreDeStageService;
import com.example.gestionstagierebackend.Services.stagiereService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stagieres")

public class StagiereController {
    @Autowired
    stagiereService stagiereService;
    @Autowired
    offreDeStageRepository offreRepo;
    @Autowired
    offreDeStageService offreService;
    @GetMapping()
   // @PreAuthorize("hasAuthority('ROLE_ROLE_ADMIN')")
    public List<Stagiere> getAllStagieres()
    {
        return stagiereService.getAllStagiere();
    }
    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Stagiere getStagiereById(@PathVariable int id )
    {
        return stagiereService.getStagiereById(id);
    }
    @PostMapping()
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Stagiere addStagiere(@RequestBody Stagiere stagiere )
    {
        return stagiereService.addStagiere(stagiere);
    }
    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void modifierStagiere(@PathVariable int id, @RequestBody Stagiere stagiere) {
        stagiereService.updateStagiere(id,stagiere);

    }
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteStagiere(@PathVariable int id) {
        stagiereService.deleteStagiere(id);
    }

    @GetMapping("/stagieresParOffres/{offreId}")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Stagiere> getStagiairesForOffre(@PathVariable int offreId) {

        offreDeStage offre = offreRepo.findById(offreId).orElse(null);

        if (offre != null) {

            return stagiereService.getStagieresByOffreDeStage(offre);
        } else {

            return null;
        }
    }
    @GetMapping("/offresPourStagiere")
    public List<offreDeStage> getAllOffres()
    {
        List<offreDeStage>offres= offreService.getAllOffres();
        for (offreDeStage offre : offres) {
            offre.setDemandes(null);
        }

        return offres;
    }
    @PostMapping("/Registre")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Stagiere Registre(@RequestBody Stagiere stagiere )
    {
        return stagiereService.Registre(stagiere);
    }
}

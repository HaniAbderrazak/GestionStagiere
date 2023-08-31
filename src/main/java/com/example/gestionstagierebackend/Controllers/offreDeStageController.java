package com.example.gestionstagierebackend.Controllers;

import com.example.gestionstagierebackend.DAO.offreDeStageRepository;
import com.example.gestionstagierebackend.Entities.offreDeStage;
import com.example.gestionstagierebackend.Services.offreDeStageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/offres")
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class offreDeStageController
{
    @Autowired
    private offreDeStageService offreService ;
    @GetMapping()
    public List<offreDeStage> getAllOffres()
    {
        return offreService.getAllOffres();
    }
    @GetMapping("/{id}")
    public offreDeStage getOffreById(@PathVariable int id )
    {
        return offreService.getOffreById(id);
    }

    @PostMapping()
    public offreDeStage addOffre(@RequestBody offreDeStage offre )
    {
        return offreService.addOffre(offre);
    }
    @PutMapping("/{id}")


    public void modifierOffre(@PathVariable int id, @RequestBody offreDeStage offre) {
        offreService.updateOffre(id,offre);

    }

    @DeleteMapping("/{id}")
    public void deleteOffre(@PathVariable int id) {
        offreService.deleteOffre(id);
    }
}

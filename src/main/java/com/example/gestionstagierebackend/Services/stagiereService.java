package com.example.gestionstagierebackend.Services;

import com.example.gestionstagierebackend.DAO.DemandeRepository;
import com.example.gestionstagierebackend.DAO.RoleRepository;
import com.example.gestionstagierebackend.DAO.offreDeStageRepository;
import com.example.gestionstagierebackend.DAO.stagiereRepository;
import com.example.gestionstagierebackend.Entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class stagiereService {
    @Autowired
    private stagiereRepository stagiereRepository ;
    @Autowired
    private DemandeRepository demandeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    public List<Stagiere> getAllStagiere()
    {
        return stagiereRepository.findAll();
    }

    public Stagiere getStagiereById( int id )
    {
        return stagiereRepository.findById(id).get();
    }

    public Stagiere addStagiere( Stagiere Stagiere )
    {
        String hashedPassword = passwordEncoder.encode(Stagiere.getMotDePasse());
        Stagiere.setMotDePasse(hashedPassword);
        return stagiereRepository.save(Stagiere);
    }
    public Stagiere Registre(Stagiere stagiere) {
        // Check if the email already exists

        if (stagiereRepository.findByEmail(stagiere.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists"); // You might want to handle this error more gracefully
        }
        else {
        String hashedPassword = passwordEncoder.encode(stagiere.getMotDePasse());
        stagiere.setMotDePasse(hashedPassword);



        return stagiereRepository.save(stagiere);}
    }
    public void deleteStagiere(int id) {
        List<Demande> list=demandeRepository.findByStagiereId(id);
        for (Demande item:list) {
            demandeRepository.delete(item);
        }
        stagiereRepository.deleteById(id);

    }
    public List<Stagiere> getStagieresByOffreDeStage(offreDeStage offre) {
        return demandeRepository.findByOffreDeStage(offre)
                .stream()
                .map(demande -> demande.getStagiere())
                .collect(Collectors.toList());
    }

    public ResponseEntity<Stagiere> updateStagiere(int id, Stagiere stagiere ) {

        Optional<Stagiere> optionalStage = stagiereRepository.findById(id);
        if (optionalStage.isPresent()) {
            Stagiere existingStagiere = optionalStage.get();
            existingStagiere.setNom(stagiere.getNom());
            existingStagiere.setPrenom(stagiere.getPrenom());
            existingStagiere.setTel(stagiere.getTel());
            existingStagiere.setEmail(stagiere.getEmail());

            String hashedPassword = passwordEncoder.encode(stagiere.getMotDePasse());
            existingStagiere.setMotDePasse(hashedPassword);


            existingStagiere.setFaculte(stagiere.getFaculte());
            existingStagiere.setCv(stagiere.getCv());

            existingStagiere.setAdresse(stagiere.getAdresse());
            existingStagiere.setImage(stagiere.getImage());


            Stagiere savedProduct = stagiereRepository.save(existingStagiere);
            return ResponseEntity.ok(savedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }


    }

}

package com.example.gestionstagierebackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Stagiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private int tel;
    private String email;
    private String motDePasse;
    private String faculte;
    private String cv;
    private String image;
    private String adresse;
    @OneToMany(mappedBy = "stagiere")
    @JsonIgnoreProperties("stagiere") // Add this annotation
    private List<Demande> demandes;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;// Set default role


}

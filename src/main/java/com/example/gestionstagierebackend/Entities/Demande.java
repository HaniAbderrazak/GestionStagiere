package com.example.gestionstagierebackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id ;
    @ManyToOne
    @JoinColumn(name = "stagiere_id")
    @JsonIgnoreProperties("demandes") // Add this annotation
    private Stagiere stagiere;

    @ManyToOne
    @JoinColumn(name = "offre_de_stage_id")
    private offreDeStage offreDeStage;
    String etat;

}

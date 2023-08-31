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
public class offreDeStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titre;
    private String description;
    @OneToMany(mappedBy = "offreDeStage")
    @JsonIgnoreProperties("offreDeStage") // Add this annotation
    private List<Demande> demandes;

public offreDeStage(String titre ,String desc)
{
    this.titre=titre;
    this.description=desc;
}


}
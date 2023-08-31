package com.example.gestionstagierebackend.DAO;

import com.example.gestionstagierebackend.Entities.offreDeStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface offreDeStageRepository extends JpaRepository<offreDeStage, Integer> {

}

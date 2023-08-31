package com.example.gestionstagierebackend.DAO;



import com.example.gestionstagierebackend.Entities.Stagiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


@RepositoryRestResource
public interface stagiereRepository extends JpaRepository<Stagiere,Integer>{

    Optional<Stagiere> findByEmail(String email);


}

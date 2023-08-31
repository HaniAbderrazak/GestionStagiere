package com.example.gestionstagierebackend;

import com.example.gestionstagierebackend.DAO.offreDeStageRepository;
import com.example.gestionstagierebackend.Entities.offreDeStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class GestionStagiereBackEndApplication {


    public static void main(String[] args) {
        SpringApplication.run(GestionStagiereBackEndApplication.class, args);
    }

}

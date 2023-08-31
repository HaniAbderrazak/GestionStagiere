package com.example.gestionstagierebackend.DAO;

import com.example.gestionstagierebackend.Entities.Role;
import com.example.gestionstagierebackend.Entities.RoleName;
import com.example.gestionstagierebackend.Entities.offreDeStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(RoleName roleName);
}

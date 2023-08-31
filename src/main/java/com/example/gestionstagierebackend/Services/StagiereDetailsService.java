package com.example.gestionstagierebackend.Services;

import com.example.gestionstagierebackend.DAO.stagiereRepository;
import com.example.gestionstagierebackend.Entities.Stagiere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StagiereDetailsService implements UserDetailsService {

    @Autowired
    private stagiereRepository stagiereRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Stagiere stagiere = stagiereRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Stagiere not found with email: " + email));

        List<SimpleGrantedAuthority> authorities = stagiere.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                stagiere.getEmail(),
                stagiere.getMotDePasse(),
                authorities
        );
    }
}

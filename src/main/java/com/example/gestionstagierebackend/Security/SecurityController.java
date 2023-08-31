package com.example.gestionstagierebackend.Security;

import com.example.gestionstagierebackend.DAO.stagiereRepository;
import com.example.gestionstagierebackend.Entities.Stagiere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.beans.Encoder;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class SecurityController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtEncoder jwtEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private stagiereRepository stagiereRepository;
    @GetMapping("/profile")
    public Authentication authentication(Authentication authentication)
    {
        return authentication;
    }
   /* @PostMapping("/login")
    public Map<String,String> login(String username,String password)
    {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password,userDetailsService));
        Instant instant=Instant.now();
        String scope=authentication.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet=JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(10, ChronoUnit.MINUTES))
                .subject(username)
                .claim("scope",scope)
                .build();
        JwtEncoderParameters jwtEncoderParameters=
                JwtEncoderParameters.from(
                        JwsHeader.with(
                                MacAlgorithm.HS512
                        ).build(),
                        jwtClaimsSet
                );
        String jwt= jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        return Map.of("access-token",jwt);
    }*/
   @PostMapping("/login")
   public Map<String,String> login(String username, String password) {
       try {
           UserDetails userDetails = userDetailsService.loadUserByUsername(username);

           Stagiere stagiere = stagiereRepository.findByEmail(username)
                   .orElseThrow(() -> new UsernameNotFoundException("Stagiere not found with email: " + username));


           if (stagiere != null) {
               UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
               Authentication authentication = authenticationManager.authenticate(authToken);

               Instant instant = Instant.now();
               String scope = userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(" "));

               JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                       .issuedAt(instant)
                       .expiresAt(instant.plus(120, ChronoUnit.MINUTES))
                       .subject(username)
                       .claim("scope", scope)
                       .claim("userId", stagiere.getId())
                       .build();

               JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(
                       JwsHeader.with(MacAlgorithm.HS512).build(),
                       jwtClaimsSet
               );

               String jwt = jwtEncoder.encode(jwtEncoderParameters).getTokenValue();

               return Map.of("access-token", jwt);
           }
           else {
               return Map.of("error","aajoa");
           }
       } catch (UsernameNotFoundException ex) {
           return Map.of("error", "Invalid credentials", "message", "Invalid username or password");

       }
   }
}

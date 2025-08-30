package it.gentlemenshairdresser.backend.controllers;

import it.gentlemenshairdresser.backend.dto.LoginRequest;
import it.gentlemenshairdresser.backend.entities.Utente;
import it.gentlemenshairdresser.backend.security.JwtUtil;
import it.gentlemenshairdresser.backend.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private JwtUtil jwtUtili;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Utente utente){
        try{
            Utente nuovoUtente = utenteService.registerNewUtente(utente);
            nuovoUtente.setPassword(null);
            return new ResponseEntity<>(nuovoUtente, HttpStatus.CREATED);
        }catch (IllegalStateException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?>loginUser(@RequestBody LoginRequest loginRequest){
        Optional<Utente> clienteOptional= utenteService.findUtenteByEmail(loginRequest.getEmail());

        if(clienteOptional.isEmpty())
            return new ResponseEntity<>("Credenziali non valide.", HttpStatus.UNAUTHORIZED);

        Utente utente =clienteOptional.get();

        if(passwordEncoder.matches(loginRequest.getPassword(), utente.getPassword())){
            String token=jwtUtili.generateToken(utente);
            return ResponseEntity.ok(token);
        }
        else return new ResponseEntity<>("Credenziali non valide.", HttpStatus.UNAUTHORIZED);
    }
}

package it.gentlemenshairdresser.backend.controllers;

import it.gentlemenshairdresser.backend.dto.LoginRequest;
import it.gentlemenshairdresser.backend.entities.Cliente;
import it.gentlemenshairdresser.backend.security.JwtUtil;
import it.gentlemenshairdresser.backend.services.ClienteService;
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
    private ClienteService clienteService;

    @Autowired
    private JwtUtil jwtUtili;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Cliente cliente){
        try{
            Cliente nuovoCliente=clienteService.registerNewCliente(cliente);
            nuovoCliente.setPassword(null);
            return new ResponseEntity<>(nuovoCliente, HttpStatus.CREATED);
        }catch (IllegalStateException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?>loginUser(@RequestBody LoginRequest loginRequest){
        Optional<Cliente> clienteOptional=clienteService.findClienteByEmail(loginRequest.getEmail());

        if(clienteOptional.isEmpty())
            return new ResponseEntity<>("Credenziali non valide.", HttpStatus.UNAUTHORIZED);

        Cliente cliente=clienteOptional.get();

        if(passwordEncoder.matches(loginRequest.getPassword(),cliente.getPassword())){
            String token=jwtUtili.generateToken(cliente);
            return ResponseEntity.ok(token);
        }
        else return new ResponseEntity<>("Credenziali non valide.", HttpStatus.UNAUTHORIZED);
    }
}

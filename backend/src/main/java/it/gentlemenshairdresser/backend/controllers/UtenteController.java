package it.gentlemenshairdresser.backend.controllers;

import it.gentlemenshairdresser.backend.entities.Utente;
import it.gentlemenshairdresser.backend.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utente")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping
    @PreAuthorize("hasRole('AMMINISTRATORE')")
    public List<Utente> getAllUtenti(){ return utenteService.findAllUtenti();}

    @GetMapping("/{id}")
    public ResponseEntity<Utente> getUtenteById(@PathVariable Long id){
        Optional<Utente> cliente= utenteService.findUtenteById(id);
        if(cliente.isPresent()) return ResponseEntity.ok(cliente.get());
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Utente createUtente(@RequestBody Utente utente){ return utenteService.saveUtente(utente);}

    @PutMapping("/{id}")
    public ResponseEntity<Utente> updateUtente(@PathVariable Long id, @RequestBody Utente utente){
        Optional<Utente> clienteOptional= utenteService.findUtenteById(id);
        if(clienteOptional.isPresent()){
            Utente utenteEsistente =clienteOptional.get();
            utenteEsistente.setNome(utente.getNome());
            utenteEsistente.setCognome(utente.getCognome());
            utenteEsistente.setEmail(utente.getEmail());
            utenteEsistente.setPassword(utente.getPassword());
            return ResponseEntity.ok(utenteService.saveUtente(utenteEsistente));
        }
        else return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('AMMINISTRATORE')")
    public ResponseEntity<Void> deleteUtenteById(@PathVariable Long id){
        if(utenteService.findUtenteById(id).isPresent()) {
            utenteService.deleteUtenteById(id);
            return ResponseEntity.noContent().build();
        }else return ResponseEntity.notFound().build();
    }
}

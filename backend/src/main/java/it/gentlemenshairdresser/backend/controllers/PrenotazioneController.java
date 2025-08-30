package it.gentlemenshairdresser.backend.controllers;

import it.gentlemenshairdresser.backend.entities.Prenotazione;
import it.gentlemenshairdresser.backend.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prenotazione")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping
    public List<Prenotazione> getAllPrenotazioni(){ return prenotazioneService.findAllPrenotazioni();}

    @GetMapping("/{id}")
    public ResponseEntity<Prenotazione> getPrenotazioneById(@PathVariable Long id){
        Optional<Prenotazione>prenotazione=prenotazioneService.findPrenotazioneById(id);
        if(prenotazione.isPresent()) return ResponseEntity.ok(prenotazione.get());
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createPrenotazione(@RequestBody Prenotazione prenotazione){
        try{
            Prenotazione nuovaPrenotazione=prenotazioneService.createPrenotazione(prenotazione);
            return new ResponseEntity<>(nuovaPrenotazione, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Prenotazione> updatePrenotazione(@PathVariable Long id, @RequestBody Prenotazione prenotazione){
        Optional<Prenotazione> prenotazioneOptional=prenotazioneService.findPrenotazioneById(id);
        if(prenotazioneOptional.isPresent()){
            Prenotazione prenotazioneEsistente=prenotazioneOptional.get();
            prenotazioneEsistente.setUtente(prenotazione.getUtente());
            prenotazioneEsistente.setBarbiere(prenotazione.getBarbiere());
            prenotazioneEsistente.setServizi(prenotazione.getServizi());
            prenotazioneEsistente.setDataOraInizio(prenotazione.getDataOraInizio());
            prenotazioneEsistente.setDataOraFine(prenotazione.getDataOraFine());
            return ResponseEntity.ok(prenotazioneService.savePrenotazione(prenotazioneEsistente));
        }
        else return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrenotazioneById(@PathVariable Long id){
        if(prenotazioneService.findPrenotazioneById(id).isPresent()) {
            prenotazioneService.deletePrenotazioneById(id);
            return ResponseEntity.noContent().build();
        }else return ResponseEntity.notFound().build();
    }
}

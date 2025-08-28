package it.gentlemenshairdresser.backend.controllers;

import it.gentlemenshairdresser.backend.entities.Barbiere;
import it.gentlemenshairdresser.backend.services.BarbiereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/barbiere")
public class BarbiereController {

    @Autowired
    private BarbiereService barbiereService;

    @GetMapping
    public List<Barbiere> getAllBarbiere(){ return barbiereService.findAllBarbiere();}

    @GetMapping("/{id}")
    public ResponseEntity<Barbiere> getBarbiereById(@PathVariable Long id) {
        Optional<Barbiere> barbiere=barbiereService.findBarbierById(id);
        if(barbiere.isPresent())
            return ResponseEntity.ok(barbiere.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Barbiere createBarbiere(@RequestBody Barbiere barbiere){ return barbiereService.saveBarbier(barbiere);}

    @PutMapping("/{id}")
    public ResponseEntity<Barbiere> updateBarbiere(@PathVariable Long id, @RequestBody Barbiere barbiere){
        Optional<Barbiere> barbiereOptional=barbiereService.findBarbierById(id);
        if(barbiereOptional.isPresent()){
            Barbiere barbiereEsistente=barbiereOptional.get();
            barbiereEsistente.setNome(barbiere.getNome());
            barbiereEsistente.setCognome(barbiere.getCognome());
            return ResponseEntity.ok(barbiereService.saveBarbier(barbiereEsistente));
        }else return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBarbiereById(@PathVariable Long id){
        if(barbiereService.findBarbierById(id).isPresent()) {
            barbiereService.deleteBarbierById(id);
            return ResponseEntity.noContent().build();
        }else return ResponseEntity.notFound().build();
    }
}

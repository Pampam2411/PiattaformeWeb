package it.gentlemenshairdresser.backend.controllers;

import it.gentlemenshairdresser.backend.entities.Servizio;
import it.gentlemenshairdresser.backend.services.ServizioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/servizi")
public class ServizioController {

    @Autowired
    private ServizioService servizioService;

    @GetMapping
    public List<Servizio> findAllServizi(){ return servizioService.findAllServices();}

    @GetMapping("/{id}")
    public ResponseEntity<Servizio> getServizioById(@PathVariable Long id){
        Optional<Servizio> servizio=servizioService.findServiceById(id);

        if(servizio.isPresent()) return ResponseEntity.ok(servizio.get());
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Servizio createServizio(@RequestBody Servizio servizio){ return servizioService.saveService(servizio);}

    @PutMapping("/{id}")
    public ResponseEntity<Servizio> updateServizio(@PathVariable Long id, @RequestBody Servizio servizio){
        Optional<Servizio> servizioOptional=servizioService.findServiceById(id);
        if(servizioOptional.isPresent()){
            Servizio servizioEsistente=servizioOptional.get();
            servizioEsistente.setNome(servizio.getNome());
            servizioEsistente.setDescrizione(servizio.getDescrizione());
            servizioEsistente.setPrezzo(servizio.getPrezzo());
            servizioEsistente.setDurataInMinuti(servizio.getDurataInMinuti());
            return ResponseEntity.ok(servizioService.saveService(servizioEsistente));
        }else return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public void deleteServizioById(@PathVariable Long id){ servizioService.deleteServiceById(id);}
    public ResponseEntity<Void> deleteServizio(@PathVariable Long id){
        if(servizioService.findServiceById(id).isPresent()) {
            servizioService.deleteServiceById(id);
            return ResponseEntity.noContent().build();
         }
        else return ResponseEntity.notFound().build();

    }
}

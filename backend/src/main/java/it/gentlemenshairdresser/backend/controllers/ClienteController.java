package it.gentlemenshairdresser.backend.controllers;

import it.gentlemenshairdresser.backend.entities.Cliente;
import it.gentlemenshairdresser.backend.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> getAllClienti(){ return clienteService.findAllClienti();}

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id){
        Optional<Cliente> cliente=clienteService.findClienteById(id);
        if(cliente.isPresent()) return ResponseEntity.ok(cliente.get());
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente){ return clienteService.saveCliente(cliente);}

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente cliente){
        Optional<Cliente> clienteOptional=clienteService.findClienteById(id);
        if(clienteOptional.isPresent()){
            Cliente clienteEsistente=clienteOptional.get();
            clienteEsistente.setNome(cliente.getNome());
            clienteEsistente.setCognome(cliente.getCognome());
            clienteEsistente.setEmail(cliente.getEmail());
            clienteEsistente.setPassword(cliente.getPassword());
            return ResponseEntity.ok(clienteService.saveCliente(clienteEsistente));
        }
        else return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClienteById(@PathVariable Long id){
        if(clienteService.findClienteById(id).isPresent()) {
            clienteService.deleteClienteById(id);
            return ResponseEntity.noContent().build();
        }else return ResponseEntity.notFound().build();
    }
}

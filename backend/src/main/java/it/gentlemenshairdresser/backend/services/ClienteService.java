package it.gentlemenshairdresser.backend.services;

import it.gentlemenshairdresser.backend.entities.Cliente;
import it.gentlemenshairdresser.backend.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAllClienti(){ return clienteRepository.findAll();}

    public Optional<Cliente> findClienteById(Long id){ return clienteRepository.findById(id);}

    public Cliente saveCliente(Cliente cliente){ return clienteRepository.save(cliente);}

    public void deleteClienteById(Long id){ clienteRepository.deleteById(id);}

}

package it.gentlemenshairdresser.backend.services;

import it.gentlemenshairdresser.backend.entities.Cliente;
import it.gentlemenshairdresser.backend.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Cliente> findAllClienti(){ return clienteRepository.findAll();}

    public Optional<Cliente> findClienteById(Long id){ return clienteRepository.findById(id);}

    public Cliente saveCliente(Cliente cliente){ return clienteRepository.save(cliente);}

    public void deleteClienteById(Long id){ clienteRepository.deleteById(id);}

    public Cliente registerNewCliente(Cliente cliente){
        if(clienteRepository.findByEmail(cliente.getEmail()).isPresent())
            throw new IllegalStateException("E-mail già registrata nel sistema.");
        String encodedPassword=passwordEncoder.encode(cliente.getPassword());
        cliente.setPassword(encodedPassword);
        return clienteRepository.save(cliente);

    }

    public Optional<Cliente> findClienteByEmail(String email) {
        return clienteRepository.findByEmail(email);

    }
}

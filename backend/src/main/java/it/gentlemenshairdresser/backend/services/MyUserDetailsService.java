package it.gentlemenshairdresser.backend.services;

import it.gentlemenshairdresser.backend.entities.Cliente;
import it.gentlemenshairdresser.backend.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Cliente> clienteOptional = clienteRepository.findByEmail(email);
        if (clienteOptional.isEmpty()) {
            throw new UsernameNotFoundException("Utente non trovato con email: " + email);
        }
        Cliente cliente = clienteOptional.get();
        // L'oggetto User è un'implementazione di UserDetails fornita da Spring Security
        return new User(cliente.getEmail(), cliente.getPassword(), new ArrayList<>()); // Per ora, nessuna autorizzazione/ruolo
    }
}

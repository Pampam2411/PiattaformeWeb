package it.gentlemenshairdresser.backend.services;

import it.gentlemenshairdresser.backend.entities.Utente;
import it.gentlemenshairdresser.backend.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Utente> clienteOptional = utenteRepository.findByEmail(email);
        if (clienteOptional.isEmpty()) {
            throw new UsernameNotFoundException("Utente non trovato con email: " + email);
        }
        Utente utente = clienteOptional.get();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + utente.getRuolo().name());
        // L'oggetto User è un'implementazione di UserDetails fornita da Spring Security
        return new User(utente.getEmail(), utente.getPassword(), Collections.singletonList(authority));
    }
}

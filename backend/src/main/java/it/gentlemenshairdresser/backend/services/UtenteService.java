package it.gentlemenshairdresser.backend.services;

import it.gentlemenshairdresser.backend.entities.Ruolo;
import it.gentlemenshairdresser.backend.entities.Utente;
import it.gentlemenshairdresser.backend.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Utente> findAllUtenti(){ return utenteRepository.findAll();}

    public Optional<Utente> findUtenteById(Long id){ return utenteRepository.findById(id);}

    public Utente saveUtente(Utente utente){ return utenteRepository.save(utente);}

    public void deleteUtenteById(Long id){ utenteRepository.deleteById(id);}

    public Utente registerNewUtente(Utente utente){
        if(utenteRepository.findByEmail(utente.getEmail()).isPresent())
            throw new IllegalStateException("E-mail già registrata nel sistema.");
        String encodedPassword=passwordEncoder.encode(utente.getPassword());
        utente.setPassword(encodedPassword);
        utente.setRuolo(Ruolo.CLIENTE);
        return utenteRepository.save(utente);

    }

    public Optional<Utente> findUtenteByEmail(String email) {
        return utenteRepository.findByEmail(email);

    }
}

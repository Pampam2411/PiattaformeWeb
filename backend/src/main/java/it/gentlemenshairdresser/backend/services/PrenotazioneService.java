package it.gentlemenshairdresser.backend.services;

import it.gentlemenshairdresser.backend.entities.Prenotazione;
import it.gentlemenshairdresser.backend.repositories.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    public List<Prenotazione> findAllPrenotazioni(){ return prenotazioneRepository.findAll();}

    public Optional<Prenotazione> findPrenotazioneById(Long id){ return prenotazioneRepository.findById(id);}

    public Prenotazione savePrenotazione(Prenotazione prenotazione){ return prenotazioneRepository.save(prenotazione);}

    public void deletePrenotazioneById(Long id){ prenotazioneRepository.deleteById(id);}
}

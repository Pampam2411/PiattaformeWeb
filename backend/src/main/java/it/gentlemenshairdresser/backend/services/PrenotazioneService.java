package it.gentlemenshairdresser.backend.services;

import it.gentlemenshairdresser.backend.entities.Prenotazione;
import it.gentlemenshairdresser.backend.entities.Servizio;
import it.gentlemenshairdresser.backend.repositories.PrenotazioneRepository;
import it.gentlemenshairdresser.backend.repositories.ServizioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private ServizioRepository servizioRepository;

    public List<Prenotazione> findAllPrenotazioni(){ return prenotazioneRepository.findAll();}

    public Optional<Prenotazione> findPrenotazioneById(Long id){ return prenotazioneRepository.findById(id);}

    public Prenotazione savePrenotazione(Prenotazione prenotazione){ return prenotazioneRepository.save(prenotazione);}

    public void deletePrenotazioneById(Long id){ prenotazioneRepository.deleteById(id);}

    public Prenotazione createPrenotazione(Prenotazione nuovaPrenotazione){
        int durataTotale= nuovaPrenotazione.getServizi().stream().mapToInt(servizio -> servizioRepository.findById(servizio.getId()).map(Servizio::getDurataInMinuti).orElse(0)).sum();

        if(durataTotale<=0)throw new IllegalArgumentException("La durata totale dei servizi deve essere maggiore di 0");

        if(nuovaPrenotazione.getBarbiere()==null)throw new IllegalArgumentException("Il barbiere deve essere specificato.");

        LocalDateTime dataInzio=nuovaPrenotazione.getDataOraInizio();
        LocalDateTime dataFine=dataInzio.plusMinutes(durataTotale);
        nuovaPrenotazione.setDataOraFine(dataFine);

        List<Prenotazione> prenotazioniSovrapposte=prenotazioneRepository.findOverlappingPrenotazioni(nuovaPrenotazione.getBarbiere().getId(),nuovaPrenotazione.getDataOraInizio(),nuovaPrenotazione.getDataOraFine());

        if(!prenotazioniSovrapposte.isEmpty())throw new IllegalStateException("Lo slot richiesto non è disponibile per il barbiere selezionato.");

        return prenotazioneRepository.save(nuovaPrenotazione);

    }
}

package it.gentlemenshairdresser.backend.services;

import it.gentlemenshairdresser.backend.entities.Servizio;
import it.gentlemenshairdresser.backend.repositories.ServizioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServizioService {

    @Autowired
    private ServizioRepository servizioRepository;

    public List<Servizio> findAllServices(){ return servizioRepository.findAll();}

    //Optional serve a gestire il caso in cui il servizio non viene trovato
    public Optional<Servizio> findServiceById(Long id){ return servizioRepository.findById(id);}

    public Servizio saveService(Servizio servizio){ return servizioRepository.save(servizio);}

    public void deleteServiceById(Long id){ servizioRepository.deleteById(id);}

}

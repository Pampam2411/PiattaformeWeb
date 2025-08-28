package it.gentlemenshairdresser.backend.services;

import it.gentlemenshairdresser.backend.entities.Barbiere;
import it.gentlemenshairdresser.backend.repositories.BarbiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BarbiereService {

    @Autowired
    private BarbiereRepository barbiereRepository;

    public List<Barbiere> findAllBarbiere(){ return barbiereRepository.findAll();}

    public Optional<Barbiere> findBarbierById(Long id){ return barbiereRepository.findById(id);}

    public Barbiere saveBarbier(Barbiere barbiere){ return barbiereRepository.save(barbiere);}

    public void deleteBarbierById(Long id){ barbiereRepository.deleteById(id);}
}

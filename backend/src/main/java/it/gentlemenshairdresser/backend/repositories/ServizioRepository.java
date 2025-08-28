package it.gentlemenshairdresser.backend.repositories;

import it.gentlemenshairdresser.backend.entities.Servizio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

@Repository
public interface ServizioRepository extends JpaRepository<Servizio,Long> {
}

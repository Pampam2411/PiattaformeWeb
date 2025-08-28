package it.gentlemenshairdresser.backend.repositories;

import it.gentlemenshairdresser.backend.entities.Barbiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarbiereRepository extends JpaRepository<Barbiere, Long> {
}

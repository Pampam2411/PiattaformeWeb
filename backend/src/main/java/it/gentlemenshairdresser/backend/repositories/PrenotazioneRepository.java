package it.gentlemenshairdresser.backend.repositories;

import it.gentlemenshairdresser.backend.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    @Query("SELECT p FROM Prenotazione p WHERE p.barbiere.id = :barbiereId AND p.dataOraInizio < :fine and p.dataOraFine > :inizio")
    List<Prenotazione> findOverlappingPrenotazioni(@Param("barbiereId")Long BarbiereId, @Param("inizio") LocalDateTime inizio, @Param("fine") LocalDateTime fine);
}

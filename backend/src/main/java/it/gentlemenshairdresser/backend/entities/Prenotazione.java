package it.gentlemenshairdresser.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_ora_inizio",nullable = false)
    private LocalDateTime dataOraInizio;

    @Column(name = "data_ora_fine",nullable = false)
    private LocalDateTime dataOraFine;

    @ManyToOne
    @JoinColumn(name = "id_cliente",nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_barbiere",nullable = false)
    private Barbiere barbiere;

    @ManyToMany
    @JoinTable(name = "prenotazione_servizi", joinColumns = @JoinColumn(name = "id_prenotazione"), inverseJoinColumns = @JoinColumn(name = "id_servizio"))
    private Set<Servizio> servizi;
}

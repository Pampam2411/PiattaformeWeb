package it.gentlemenshairdresser.backend.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "servizi")
@Getter
@Setter
public class Servizio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome",nullable = false)
    private String nome;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "prezzo", nullable = false)
    private double prezzo;

    @Column(name = "durata_minuti",nullable = false)
    private int durataInMinuti;
}

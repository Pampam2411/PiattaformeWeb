package it.gentlemenshairdresser.backend.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "barbiere")
@Getter
@Setter
public class Barbiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome",nullable = false)
    private String nome;

    @Column(name = "cognome",nullable = false)
    private String cognome;

}

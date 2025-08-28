package it.gentlemenshairdresser.backend.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clienti")
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cognome;
    @Column(unique = true)
    private String email;
    private String password;


}
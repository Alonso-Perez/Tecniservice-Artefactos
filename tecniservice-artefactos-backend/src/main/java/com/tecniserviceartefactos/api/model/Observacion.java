package com.tecniserviceartefactos.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "observaciones")
@Data
public class Observacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT") //Permite textos largos
    private String descripción;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoObservacion tipo; //Diagnostico, Reparación, Etc.

    @Column(nullable = false)
    private LocalDateTime fecha;

    //Relación con el equipo (Muchos a uno)
    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;

    //Relación con el Usuario que escribió la nota (Técnico o Admin)
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    //Enum Interno para tipos
    public enum TipoObservacion{
        DIAGNOSTICO,
        REPARACIÓN,
        NOTA_INTERNA
    }

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now();
    }

}

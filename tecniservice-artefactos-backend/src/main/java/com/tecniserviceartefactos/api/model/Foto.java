package com.tecniserviceartefactos.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "fotos")
@Data
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreArchivo;
    private String ruta;

    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;

}

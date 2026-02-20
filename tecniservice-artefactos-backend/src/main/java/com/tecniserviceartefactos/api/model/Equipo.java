package com.tecniserviceartefactos.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "equipos")
@Data
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) //Obligatorio en BD
    private String tipo;
    @Column(nullable = false) //Obligatorio en BD
    private String marca;
    @Column(nullable = true)  //Opcional
    private String modelo;
    @Column(nullable = true) //Opcional
    private String serie;

    @ManyToOne
    @JoinColumn(name = "orden_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //Spring entenderá que este campo es solo paraa guardar, pero no lo muestres cuando conviertas a JSON
    private OrdenServicio ordenServicio;

    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL)
    private List<Observacion> observaciones;

    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL)
    private List<Foto> fotos;
}

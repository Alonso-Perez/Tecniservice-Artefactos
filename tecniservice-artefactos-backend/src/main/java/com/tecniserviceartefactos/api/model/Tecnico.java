package com.tecniserviceartefactos.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tecnicos")
@Data
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Tecnico extends Usuario {

    private String especialidad; //Refrigeracion, Lavadoras
    private boolean disponible; //Para saber si puede recibir trabajos

}

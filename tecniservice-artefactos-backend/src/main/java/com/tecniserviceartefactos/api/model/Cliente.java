package com.tecniserviceartefactos.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "clientes")
@Data
@EqualsAndHashCode(callSuper = true) //Esto es importante para que Lombok considere el ID del padre
@PrimaryKeyJoinColumn(name = "usuario_id") //Une la tabla clientes con usuarios
public class Cliente extends Usuario {
    private String direccion;
}

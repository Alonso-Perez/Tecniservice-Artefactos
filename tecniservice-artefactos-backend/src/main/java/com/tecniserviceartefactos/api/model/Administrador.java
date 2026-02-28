package com.tecniserviceartefactos.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "administradores")
@Data
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Administrador extends Usuario {
    private String departamento;

    @Column(nullable = false)
    @Email(message = "El formato del correo electrónico es inválido")
    private String email;

}

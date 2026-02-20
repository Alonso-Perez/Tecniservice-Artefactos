package com.tecniserviceartefactos.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email; //Para poder asegurarnos de que exista formato de correo electrónico
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidoPaterno;

    @Column(nullable = false)
    private String apellidoMaterno;

    @Column(nullable = false)
    @Email(message = "El formato del correo electrónico es inválido")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String telefono;

    //Un campo para saber el rol rápidamente sin hacer joins complejos
    @Enumerated(EnumType.STRING)
    private Rol rol;

    public enum Rol {
        ADMIN, CLIENTE, TECNICO
    }

}

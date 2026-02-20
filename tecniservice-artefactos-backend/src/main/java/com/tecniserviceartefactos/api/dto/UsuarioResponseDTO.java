package com.tecniserviceartefactos.api.dto;

import com.tecniserviceartefactos.api.model.Usuario.Rol;

import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String email;
    private String telefono;
    private Rol rol;
}

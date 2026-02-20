package com.tecniserviceartefactos.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EquipoUpdateDTO {
    //Usamos este DTO cuando el técnico ya está al frente del equipo
    // y puede visualizar el modelo y serie del equipo

    @NotBlank(message = "El modelo es obligatorio para actualizar")
    private String modelo;

    @NotBlank(message = "El número de serie es obligatorio para actualizar")
    private String serie;
}

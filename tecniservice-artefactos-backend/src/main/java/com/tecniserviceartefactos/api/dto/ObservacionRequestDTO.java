package com.tecniserviceartefactos.api.dto;

import com.tecniserviceartefactos.api.model.Observacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ObservacionRequestDTO {

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull(message = "Debe especificar el tipo de observación")
    private Observacion.TipoObservacion tipo;
}

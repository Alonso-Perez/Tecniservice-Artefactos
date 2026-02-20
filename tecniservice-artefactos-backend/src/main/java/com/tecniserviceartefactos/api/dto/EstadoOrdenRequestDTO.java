package com.tecniserviceartefactos.api.dto;

import com.tecniserviceartefactos.api.model.OrdenServicio.EstadoOrden;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstadoOrdenRequestDTO {

    @NotNull(message = "El nuevo estado es obligatorio")
    private EstadoOrden estado; //Usamos el enum
}

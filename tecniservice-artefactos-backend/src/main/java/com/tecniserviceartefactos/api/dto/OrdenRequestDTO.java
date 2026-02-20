package com.tecniserviceartefactos.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class OrdenRequestDTO { //Este es el objeto principal que enviará el Administrador desde el FrontEnd

    //Usamos Long para IDs referenciales
    //@NotNull = El vampo debe venir en el JSON, no puede ser nulo
    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "El ID del técnico es obligatorio")
    private Long tecnicoId;

    //Asumimos que enviamos el ID del administrador que está usando el sistema
    private Long administradorId;

    //Nos proveerá un delimitador de caracteres que no se deberá exceder
    @Size(max = 1000, message = "La descripción no puede exceder los 1000 caracteres")
    private String descripcionProblema;

    //Una lista de equipos. Validamos que la lista no venga vacía
    @NotNull(message = "Debe haber al menos un equipo involucrado")
    @Size(min = 1, message = "La orden debe tener al menos un equipo")
    private List<EquipoRequestDTO> equipos;

}

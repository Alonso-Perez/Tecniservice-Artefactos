package com.tecniserviceartefactos.api.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrdenResponseDTO {
    private Long id;
    private String descripcionProblema;
    private String estado;
    private LocalDateTime fechaCreacion;

    //En lugar de devolver el objeto Cliente entero con password,
    //solo devolvemos lo necesario

    private String nombreCliente;
    private String nombreTecnico;

    //Devolvemos la lista de DTOs de equipos, NO de Entidades
    private List<EquipoResponseDTO> equipos;
}

package com.tecniserviceartefactos.api.dto;

import lombok.Data;

@Data
public class EquipoResponseDTO {
    private Long id;
    private String tipo;
    private String marca;
    private String modelo;
    private String serie;
    //Nota: Aquí NO ponemos la Orden completa, para evitar el bucle.
}
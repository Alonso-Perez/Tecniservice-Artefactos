package com.tecniserviceartefactos.api.dto;

import lombok.Data;

@Data
public class FotoResponseDTO {
    private Long id;
    private String nombreArchivo; // El nombre UUID generado
    private String URL; //La URL para descargarla/verla
}

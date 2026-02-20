package com.tecniserviceartefactos.api.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ObservacionResponseDTO {
    private Long id;
    private String descripcion;
    private String tipo;
    private LocalDateTime fechaRegistro;
    private String nombreAutor; //Solo enviamos el nombre, no el objeto Usuario entero
}

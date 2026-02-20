package com.tecniserviceartefactos.api.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ErrorDTO { //Definiremos cómo lucirá el mensaje de Error estandarizado

    private LocalDateTime fecha;
    private int estado; //Ej: 400, 404, 500
    private String error; //Ej: "Bad Request"
    private String mensaje; //Ej: "El Usuario no existe"
    private Map<String, String> validaciones; //Para errores de campos (ej: email inválido)

}

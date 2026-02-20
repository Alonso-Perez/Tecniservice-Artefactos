package com.tecniserviceartefactos.api.exception;

public class RecursoNoEncontradoException extends RuntimeException { //Creación de excepción específica por si lo toma como error interno
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}

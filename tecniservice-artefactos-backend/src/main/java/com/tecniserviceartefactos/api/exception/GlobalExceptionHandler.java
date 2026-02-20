package com.tecniserviceartefactos.api.exception;

import com.tecniserviceartefactos.api.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * CASO 1: No se encuentra algo en la BD (404 NOT FOUND)
     */
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorDTO> handleRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setFecha(LocalDateTime.now());
        error.setEstado(HttpStatus.NOT_FOUND.value());
        error.setError("Recurso No Encontrado");
        error.setMensaje(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * CASO 2: Error de Validaciones @Valid (400 BAD REQUEST)
     * Ejemplo: Email mal formado, password corto, campo vacío.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidaciones(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        // Extraemos cada campo que falló y su mensaje
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errores.put(error.getField(), error.getDefaultMessage());
        });

        ErrorDTO errorResponse = new ErrorDTO();
        errorResponse.setFecha(LocalDateTime.now());
        errorResponse.setEstado(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError("Validación Fallida");
        errorResponse.setMensaje("Se encontraron errores en los datos enviados");
        errorResponse.setValidaciones(errores); // Adjuntamos el mapa

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * CASO 3: Errores de Lógica de Negocio (400 BAD REQUEST)
     * Ejemplo: "Acceso Denegado" que lanzamos manualmente o "Email duplicado".
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> handleGeneral(RuntimeException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setFecha(LocalDateTime.now());
        error.setEstado(HttpStatus.BAD_REQUEST.value());
        error.setError("Error de Negocio"); // O "Bad Request"
        error.setMensaje(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * CASO 4: Error en el sistema de almacenamiento (500 INTERNAL SERVER ERROR)
     * Disco lleno, sin permisos, fallo de IO.
     */
    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ErrorDTO> handleStorageException(StorageException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setFecha(LocalDateTime.now());
        error.setEstado(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setError("Error de Almacenamiento");
        error.setMensaje(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * CASO 5: Conflicto de recursos (409 CONFLICT)
     * Se usa cuando intentamos crear algo que ya existe (Email duplicado, Serie duplicada).
     */
    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<ErrorDTO> handleRecursoDuplicado(RecursoDuplicadoException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setFecha(LocalDateTime.now());
        error.setEstado(HttpStatus.CONFLICT.value()); // 409
        error.setError("Conflicto de Datos"); // O "Recurso Duplicado"
        error.setMensaje(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    // Podríamos agregar uno más para Exception.class (Error 500 genérico) si quisiéramos ocultar errores de sistema.

}

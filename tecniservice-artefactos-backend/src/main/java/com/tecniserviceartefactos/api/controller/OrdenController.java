package com.tecniserviceartefactos.api.controller;

import com.tecniserviceartefactos.api.dto.*;
import com.tecniserviceartefactos.api.model.OrdenServicio;
import com.tecniserviceartefactos.api.model.Equipo;
import com.tecniserviceartefactos.api.service.OrdenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ordenes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class OrdenController {

    private final OrdenService ordenService;

    // 1. CREAR ORDEN (Solo Admin)
    @PostMapping
    public ResponseEntity<OrdenServicio> crearOrden(@Valid @RequestBody OrdenRequestDTO dto) {
        // @Valid activa las validaciones del DTO. Si fallan, salta el GlobalHandler (400 Bad Request)
        OrdenServicio nuevaOrden = ordenService.crearOrden(dto);
        return new ResponseEntity<>(nuevaOrden, HttpStatus.CREATED);
    }

    // 2. LISTAR ORDENES (Admin ve todo, Técnico ve lo suyo)
    @GetMapping
    public ResponseEntity<List<OrdenServicio>> listarOrdenes(Authentication authentication) {
        // Spring Security nos da el objeto authentication con los datos del usuario logueado
        String email;

        //Verificacion: Si entramos desde Angular sin Login, Authentication será null

        if(authentication == null){
            email = "alonsompguanilo@gmail.com";
            System.out.println("⚠️ Usuario anónimo detectado. Usando email de prueba: " + email);
        }
        else{
            email = authentication.getName();
        }

        List<OrdenServicio> ordenes = ordenService.listarOrdenes(email);
        return ResponseEntity.ok(ordenes);
    }

    // 3. OBTENER UNA ORDEN POR ID (Aquí probaremos el 404)
    @GetMapping("/{id}")
    public ResponseEntity<OrdenServicio> obtenerPorId(@PathVariable Long id) {
        // Si no existe, el servicio lanza RecursoNoEncontradoException
        // El Controller ni se entera, el GlobalHandler responde el JSON bonito.
        OrdenServicio orden = ordenService.obtenerOrdenPorId(id);
        return ResponseEntity.ok(orden);
    }

    // 4. CAMBIAR ESTADO
    @PatchMapping("/{id}/estado")
    public ResponseEntity<OrdenServicio> cambiarEstado(
            @PathVariable Long id,
            @RequestParam OrdenServicio.EstadoOrden nuevoEstado, // Pasamos el estado como Query Param (?nuevoEstado=EN_PROCESO)
            Authentication authentication) {

        String email = authentication.getName();
        OrdenServicio ordenActualizada = ordenService.cambiarEstadoOrden(id, nuevoEstado, email);
        return ResponseEntity.ok(ordenActualizada);
    }

    // 5. ACTUALIZAR EQUIPO (Endpoint anidado)
    @PatchMapping("/equipos/{equipoId}")
    public ResponseEntity<Equipo> actualizarEquipo(
            @PathVariable Long equipoId,
            @Valid @RequestBody EquipoUpdateDTO dto) {

        Equipo equipo = ordenService.actualizarDatosEquipo(equipoId, dto);
        return ResponseEntity.ok(equipo);
    }

    // 6. AGREGAR OBSERVACIÓN A UN EQUIPO
    @PostMapping("/equipos/{equipoId}/observaciones")
    public ResponseEntity<ObservacionResponseDTO> agregarObservacion(
            @PathVariable Long equipoId,
            @Valid @RequestBody ObservacionRequestDTO dto,
            Authentication authentication) {

        String email = authentication.getName();
        ObservacionResponseDTO response = ordenService.agregarObservacion(equipoId, dto, email);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 7. ELIMINAR ORDEN (DELETE)
    // Este permite que el frontend diga: "Borra la orden 5"
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOrden(@PathVariable Long id) {
        // Llama al servicio de eliminar que creamos
        ordenService.eliminarOrden(id);

        // En los DELETE, el estándar es devolver "204 No Content"
        // Significa: "Lo borré con éxito, no tengo nada que mostrarte de vuelta".
        ResponseEntity<Void> build = ResponseEntity.noContent().build();
        return build;
    }
}

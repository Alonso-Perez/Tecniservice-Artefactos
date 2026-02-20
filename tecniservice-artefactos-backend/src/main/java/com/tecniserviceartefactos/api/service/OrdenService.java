package com.tecniserviceartefactos.api.service;

import com.tecniserviceartefactos.api.dto.EquipoUpdateDTO;
import com.tecniserviceartefactos.api.dto.ObservacionRequestDTO;
import com.tecniserviceartefactos.api.dto.ObservacionResponseDTO;
import com.tecniserviceartefactos.api.dto.OrdenRequestDTO;
import com.tecniserviceartefactos.api.model.OrdenServicio;
import com.tecniserviceartefactos.api.model.Equipo;
import com.tecniserviceartefactos.api.model.OrdenServicio.EstadoOrden;

import java.util.List;

//Esta interfaz me ayudará a definir el QUÉ SE PUEDE HACER, pero no el CÓMO, eso se hará en la implementación

public interface OrdenService {
    //Método para crear una órden
    //Recibe el DTO (datos limpios) y retorna la Entidad creada (o un DTO de respuesta)
    OrdenServicio crearOrden(OrdenRequestDTO ordenRequestDTO);

    //Método para actualización de datos
    Equipo actualizarDatosEquipo(Long equipoId, EquipoUpdateDTO updateDTO);

    //Método recibe el ID del equipo, el DTO y el email del usuario logueado
    ObservacionResponseDTO agregarObservacion(Long equipoId, ObservacionRequestDTO observacionRequestDTO, String emailAutor);

    //Nuevo método para listar
    List<OrdenServicio> listarOrdenes(String emailUsuario);

    OrdenServicio cambiarEstadoOrden(Long idOrden, EstadoOrden nuevoEstado, String emailUsuario);

    OrdenServicio obtenerOrdenPorId(Long idOrden);

    void eliminarOrden(Long id);
}

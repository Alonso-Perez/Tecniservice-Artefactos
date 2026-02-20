package com.tecniserviceartefactos.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.tecniserviceartefactos.api.dto.*;
import com.tecniserviceartefactos.api.model.*;
import com.tecniserviceartefactos.api.repository.*;
import com.tecniserviceartefactos.api.service.OrdenService;
import com.tecniserviceartefactos.api.model.OrdenServicio.EstadoOrden;
// Asegúrate de importar tu excepción
import com.tecniserviceartefactos.api.exception.RecursoNoEncontradoException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements OrdenService {

    private final OrdenServicioRepository ordenServicioRepository;
    private final UsuarioRepository usuarioRepository;
    private final TecnicoRepository tecnicoRepository;
    private final EquipoRepository equipoRepository;
    private final ObservacionRepository observacionRepository;

    @Override
    @Transactional
    public OrdenServicio crearOrden(OrdenRequestDTO dto){

        // 1. Buscar Entidades Relacionadas
        // CAMBIO: Si no encuentra el cliente/admin/técnico, debe devolver 404, no 500.
        Usuario cliente = usuarioRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + dto.getClienteId())); // <--- CAMBIO AQUÍ

        Usuario administrador = usuarioRepository.findById(dto.getAdministradorId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Administrador no encontrado con ID: " + dto.getAdministradorId())); // <--- CAMBIO AQUÍ

        Tecnico tecnico = tecnicoRepository.findById(dto.getTecnicoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Tecnico no encontrado con ID: " + dto.getTecnicoId())); // <--- CAMBIO AQUÍ

        // Validacion extra de negocio (Tipos de usuario)
        // NOTA: Aquí ESTÁ BIEN dejar RuntimeException (o usar IllegalArgumentException)
        // porque el usuario SÍ existe, pero el dato es inválido. No es un "Not Found".
        if(!(cliente instanceof Cliente)){
            throw new RuntimeException("El usuario proporcionado no es un Cliente válido");
        }
        if(!(administrador instanceof Administrador)){
            throw new RuntimeException("El usuario proporcionado no es un Administrador válido");
        }

        // 2. Crear y mapear la órden
        OrdenServicio orden = new OrdenServicio();
        orden.setCliente((Cliente) cliente);
        orden.setAdministrador((Administrador) administrador);
        orden.setTecnico(tecnico);
        orden.setDescripcionProblema(dto.getDescripcionProblema());
        orden.setEstado(OrdenServicio.EstadoOrden.PENDIENTE);

        // 3. PROCESAR LOS EQUIPOS
        List<Equipo> listaEquipos = new ArrayList<>();

        for(EquipoRequestDTO equipoDTO : dto.getEquipos()){
            Equipo equipo = new Equipo();
            equipo.setTipo(equipoDTO.getTipo());
            equipo.setMarca(equipoDTO.getMarca());
            equipo.setModelo(equipoDTO.getModelo());
            equipo.setSerie(equipoDTO.getSerie());
            equipo.setOrdenServicio(orden);
            listaEquipos.add(equipo);
        }
        orden.setEquipos(listaEquipos);

        return ordenServicioRepository.save(orden);
    }

    @Override
    @Transactional
    public Equipo actualizarDatosEquipo(Long equipoId, EquipoUpdateDTO dto){
        // CAMBIO: Si el equipo no existe para actualizarlo, es un 404.
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Equipo no encontrado con ID: " + equipoId)); // <--- CAMBIO AQUÍ

        equipo.setModelo(dto.getModelo());
        equipo.setSerie(dto.getSerie());

        return equipoRepository.save(equipo);
    }

    @Override
    @Transactional
    public ObservacionResponseDTO agregarObservacion(Long equipoId, ObservacionRequestDTO dto, String emailAutor){

        // CAMBIO: Validación de existencia del equipo
        Equipo equipo = equipoRepository.findById(equipoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Equipo no encontrado con ID: " + equipoId)); // <--- CAMBIO AQUÍ

        // CAMBIO: Validación de existencia del autor
        Usuario autor = usuarioRepository.findByEmail(emailAutor)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario autor no encontrado: " + emailAutor)); // <--- CAMBIO AQUÍ

        Observacion observacion = new Observacion();
        observacion.setDescripción(dto.getDescripcion());
        observacion.setTipo(dto.getTipo());
        observacion.setEquipo(equipo);
        observacion.setAutor(autor);

        Observacion guardada =  observacionRepository.save(observacion);

        ObservacionResponseDTO response = new ObservacionResponseDTO();
        response.setId(guardada.getId());
        response.setDescripcion(dto.getDescripcion());
        response.setTipo(dto.getTipo().toString());
        response.setFechaRegistro(guardada.getFecha());
        response.setNombreAutor(autor.getNombres() +  " " +  autor.getApellidoPaterno());

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdenServicio> listarOrdenes(String emailUsuario){

        // CAMBIO: Si el usuario que intenta listar no existe en BD
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + emailUsuario)); // <--- CAMBIO AQUÍ

        if(usuario.getRol() == Usuario.Rol.ADMIN){
            return ordenServicioRepository.findAllByOrderByFechaCreacionDesc();
        }else if(usuario.getRol() == Usuario.Rol.TECNICO){
            return ordenServicioRepository.findByTecnicoIdOrderByFechaCreacionDesc(usuario.getId());
        }else{
            return List.of();
        }
    }

    @Override
    @Transactional
    public OrdenServicio cambiarEstadoOrden(Long idOrden, EstadoOrden nuevoEstado, String emailUsuario){

        // Este ya lo tenías bien:
        OrdenServicio orden = ordenServicioRepository.findById(idOrden)
                .orElseThrow(() -> new RecursoNoEncontradoException("Orden no encontrada con ID: " + idOrden));

        // CAMBIO: Si el usuario del token no está en BD
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + emailUsuario)); // <--- CAMBIO AQUÍ

        // NOTA IMPORTANTE DE SEGURIDAD:
        // Aquí dejamos RuntimeException (o AccesoDenegadoException si crearas una).
        // NO uses RecursoNoEncontradoException aquí.
        // ¿Por qué? Porque la orden SÍ existe y el usuario SÍ existe.
        // El problema es que NO TIENE PERMISO. Eso es error 403, no 404.
        if(usuario.getRol() == Usuario.Rol.TECNICO){
            if(!orden.getTecnico().getId().equals(usuario.getId())){
                throw new RuntimeException("ACCESO DENEGADO: No puedes modificar una órden que no te pertenece");
            }
        }

        orden.setEstado(nuevoEstado);
        return ordenServicioRepository.save(orden);
    }

    @Override
    @Transactional(readOnly = true)
    public OrdenServicio obtenerOrdenPorId(Long id) {
        return ordenServicioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Orden no encontrada con ID: " + id));
    }

    @Override
    @Transactional
    public void eliminarOrden(Long id) {
        // Primero verificamos si existe. Si no, ¡404!
        if (!ordenServicioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No se puede eliminar. Orden no encontrada con ID: " + id);
        }

        // Si existe, procedemos
        ordenServicioRepository.deleteById(id);
    }
}

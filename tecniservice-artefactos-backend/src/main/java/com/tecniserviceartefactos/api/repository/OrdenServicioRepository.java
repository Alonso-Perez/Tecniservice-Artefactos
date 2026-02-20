package com.tecniserviceartefactos.api.repository;

import com.tecniserviceartefactos.api.model.OrdenServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrdenServicioRepository extends JpaRepository<OrdenServicio,Long> {
    //1. Para que el técnico solo vea sus órdenes asignadas
    List<OrdenServicio> findByTecnicoId(Long tecnicoId);

    //2. Para filtrar por estado (ej: Ver todas las Pendientes)
    List<OrdenServicio> findByEstado(OrdenServicio.EstadoOrden estado);

    //3. Combinado: Órdenes de un técnico específico que estén EN_PROCESO
    List<OrdenServicio> findByTecnicoIdAndEstado(Long tecnicoId, OrdenServicio.EstadoOrden estado);

    //4. Buscar órdenes de un cliente específico
    List<OrdenServicio> findByClienteId(Long clienteId);

    List<OrdenServicio> findByTecnicoIdOrderByFechaCreacionDesc(Long tecnicoId);
    List<OrdenServicio> findAllByOrderByFechaCreacionDesc();
}

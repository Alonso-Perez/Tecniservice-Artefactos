package com.tecniserviceartefactos.api.repository;

import com.tecniserviceartefactos.api.model.Observacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservacionRepository extends JpaRepository<Observacion, Long> {
//Herencia de JpaRepository, se genera save(), findById(), delete(), sin escribir ni una línea de SQL


    //Metodo derivado: Spring crea el SQL automáticamente al leer el nombre.
    //Nos servirá para listar todas las observaciones de un equipo específico.
    List<Observacion> findByEquipoId(Long equipoId);
}

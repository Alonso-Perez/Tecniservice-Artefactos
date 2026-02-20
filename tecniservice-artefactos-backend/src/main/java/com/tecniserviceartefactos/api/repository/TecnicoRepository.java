package com.tecniserviceartefactos.api.repository;

import com.tecniserviceartefactos.api.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Long>{
    //Buscar técnicos que estén disponibles
    List<Tecnico> findByDisponibleTrue();

    //Buscar técnicos por especialidad (ej.: "Lavadoras")
    List<Tecnico> findByEspecialidad(String especialidad);
}

package com.tecniserviceartefactos.api.repository;

import com.tecniserviceartefactos.api.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo,Long> {
    boolean existsBySerie(String serie);
}

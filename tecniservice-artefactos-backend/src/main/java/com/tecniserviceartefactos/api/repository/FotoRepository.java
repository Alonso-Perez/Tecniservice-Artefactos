package com.tecniserviceartefactos.api.repository;

import com.tecniserviceartefactos.api.model.Foto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends  JpaRepository<Foto,Long> {
}

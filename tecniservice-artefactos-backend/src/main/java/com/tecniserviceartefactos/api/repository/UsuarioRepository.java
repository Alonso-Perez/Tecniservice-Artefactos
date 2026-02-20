package com.tecniserviceartefactos.api.repository;

import com.tecniserviceartefactos.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Método mágico: Spring crea el SQL automáticamente al leer el nombre del método
    Optional<Usuario> findByEmail(String email);

    //Para validar si existe el email al registrar
    boolean existsByEmail(String email);
}

package com.tecniserviceartefactos.api.controller;

import com.tecniserviceartefactos.api.dto.RegistroRequestDTO;
import com.tecniserviceartefactos.api.dto.UsuarioResponseDTO;
import com.tecniserviceartefactos.api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register-tecnico")
    public ResponseEntity<UsuarioResponseDTO> registrarTecnico(@Valid @RequestBody RegistroRequestDTO dto) {
        UsuarioResponseDTO nuevoUsuario = authService.registrarTecnico(dto);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }
}

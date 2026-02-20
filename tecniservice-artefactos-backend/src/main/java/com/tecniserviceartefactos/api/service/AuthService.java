package com.tecniserviceartefactos.api.service;

import com.tecniserviceartefactos.api.dto.RegistroRequestDTO;
import com.tecniserviceartefactos.api.dto.UsuarioResponseDTO;

public interface AuthService {
    UsuarioResponseDTO registrarTecnico(RegistroRequestDTO registroRequestDTO);
}

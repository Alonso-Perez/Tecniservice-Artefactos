package com.tecniserviceartefactos.api.service.impl;

import com.tecniserviceartefactos.api.dto.RegistroRequestDTO;
import com.tecniserviceartefactos.api.dto.UsuarioResponseDTO;
import com.tecniserviceartefactos.api.exception.RecursoDuplicadoException;
import com.tecniserviceartefactos.api.model.Tecnico;
import com.tecniserviceartefactos.api.model.Usuario;
import com.tecniserviceartefactos.api.repository.UsuarioRepository;
import com.tecniserviceartefactos.api.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; //Inyectamos

    @Override
    public UsuarioResponseDTO registrarTecnico(RegistroRequestDTO registroRequestDTO) {
        //1. Validar duplicados
        if(usuarioRepository.existsByEmail(registroRequestDTO.getEmail())){
            throw new RecursoDuplicadoException("Email existente: " + registroRequestDTO.getEmail());
        }

        //2. Crear entidad Técnico
        Tecnico tecnico = new Tecnico();
        tecnico.setNombres(registroRequestDTO.getNombre());
        tecnico.setApellidoPaterno(registroRequestDTO.getApellidoPaterno());
        tecnico.setApellidoMaterno(registroRequestDTO.getApellidoMaterno());
        tecnico.setEmail(registroRequestDTO.getEmail());
        tecnico.setRol(Usuario.Rol.TECNICO);

        //3. Encriptar password (Crucial)
        tecnico.setPassword(passwordEncoder.encode(registroRequestDTO.getPassword()));

        //4. Guardar
        Usuario guardado =  usuarioRepository.save(tecnico);

        //5. Mapear las respuestas (Para confirmar al Admin que se creó)
        UsuarioResponseDTO response = new  UsuarioResponseDTO();
        response.setId(guardado.getId());
        response.setNombres(guardado.getNombres());
        response.setEmail(guardado.getEmail());
        response.setRol(guardado.getRol());

        return response;

    }


}

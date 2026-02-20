package com.tecniserviceartefactos.api.service.impl;

//Spring Security no sabe que tienes una tabla usuarios ni que uso Rol. Se necesita un puente

import com.tecniserviceartefactos.api.model.Usuario;
import com.tecniserviceartefactos.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUsersDetailService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //1. Buscamos el usuario por su email (que usaremos como "username")
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));

        //2. Covertimos nuestro Rol (Enum) a una Authority de Spring
        //Spring Security espera que los roles empiecen con "ROLE_"
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name());

        //3. Retornamos el objeto User de Spring (no nuestra entidad)
        return new User(usuario.getEmail(), usuario.getPassword(), Collections.singletonList(authority));
    }
}

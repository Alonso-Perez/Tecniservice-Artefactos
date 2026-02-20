package com.tecniserviceartefactos.api.config;

import com.tecniserviceartefactos.api.model.Administrador;
import com.tecniserviceartefactos.api.model.Usuario;
import com.tecniserviceartefactos.api.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner{

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception{

        String emailAdmin = "alonsompguanilo@gmail.com";

        //Verificar si existe un admin, si no, crearlo
        if(!usuarioRepository.existsByEmail("emailAdmin")){
            Administrador admin = new Administrador();
            admin.setNombres("Alonso Manuel");
            admin.setApellidoPaterno("Pérez");
            admin.setApellidoMaterno("Guanilo");
            admin.setEmail(emailAdmin); //Se guarda el Gmail/Hotmail
            admin.setTelefono("904372029");

            //Cambiar la contraseña si se hará uso de esta
            admin.setPassword(passwordEncoder.encode("Alonso Manuel"));

            admin.setRol(Usuario.Rol.ADMIN);
            admin.setDepartamento("Gerencia");

            usuarioRepository.save(admin);
            System.out.println("--- Admin creado: " + emailAdmin + " ---");
        }
    }

}
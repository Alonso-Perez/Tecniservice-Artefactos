package com.tecniserviceartefactos.api.config;

import com.tecniserviceartefactos.api.model.Administrador;
import com.tecniserviceartefactos.api.model.Cliente;
import com.tecniserviceartefactos.api.model.OrdenServicio;
import com.tecniserviceartefactos.api.model.Usuario;
import com.tecniserviceartefactos.api.repository.UsuarioRepository;
import com.tecniserviceartefactos.api.repository.OrdenServicioRepository;


import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner{

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrdenServicioRepository ordenServicioRepository;

    @Override
    public void run(String... args) throws Exception{

        String emailAdmin = "alonsompguanilo@gmail.com";

        //Verificar si existe un admin, si no, crearlo
        if(!usuarioRepository.existsByEmail(emailAdmin)){
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

        if(ordenServicioRepository.count()==0){
            OrdenServicio ordenPrueba = new OrdenServicio();

            ordenPrueba.setDescripcionProblema("Mantenimiento de lavadora Whirlpool");

            Cliente cliente = new Cliente();

            cliente.setNombres("Laura Daniela");
            cliente.setApellidoPaterno("Rospigliosi");
            cliente.setApellidoMaterno("Coronado");
            cliente.setDireccion("Av. Paseo de la República 3557");
            cliente.setEmail("lauradinha@gmail.com");
            cliente.setPassword(passwordEncoder.encode("laurinha12345"));

            ordenPrueba.setCliente(cliente);
            Administrador admin =  usuarioRepository.findByEmail();
            ordenPrueba.setAdministrador();
        }

    }

}
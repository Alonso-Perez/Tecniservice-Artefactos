package com.tecniserviceartefactos.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration; // IMPORTANTE
import org.springframework.web.cors.UrlBasedCorsConfigurationSource; // IMPORTANTE
import java.util.List; // IMPORTANTE

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Desactivar CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // 2. CONFIGURACIÓN CORS (ESTO ES LO NUEVO QUE NECESITAS)
                // Le dice a Spring Security que permita entrar a localhost:4200
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:4200"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                    config.setAllowedHeaders(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))

                // 3. Configurar permisos de rutas
                .authorizeHttpRequests(auth -> auth
                        // Permitir ver imágenes (Público)
                        .requestMatchers(HttpMethod.GET, "/api/v1/media/**").permitAll()

                        // 👇 AGREGADO: Permitir que Angular LEA la lista de órdenes sin loguearse (por ahora)
                        .requestMatchers(HttpMethod.GET, "/api/v1/ordenes").permitAll()

                        // Solo ADMIN puede crear órdenes
                        .requestMatchers(HttpMethod.POST, "/api/v1/ordenes").hasRole("ADMIN")

                        // Técnicos y Admin pueden actualizar equipos
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/ordenes/equipos/**").hasAnyRole("ADMIN", "TECNICO")

                        // Solo ADMIN puede registrar técnicos
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/register-tecnico").hasRole("ADMIN")

                        // El resto requiere autenticación
                        .anyRequest().authenticated()
                )

                // 4. Autenticación Básica
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}

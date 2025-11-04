package com.agrominerva.servicio_usuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Creamos un "Bean" del encriptador
    // Un Bean es un objeto que Spring maneja y podemos "inyectar" donde queramos
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Configuramos la seguridad web
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitamos CSRF (común en APIs REST)
            .authorizeHttpRequests(auth -> auth
                // Permitimos que cualquiera acceda a nuestras rutas de API (por ahora)
                // Más adelante aquí se configuraría JWT
                .requestMatchers("/api/**").permitAll() 
                .anyRequest().authenticated() // Cualquier otra ruta requiere autenticación
            );
        return http.build();
    }
}
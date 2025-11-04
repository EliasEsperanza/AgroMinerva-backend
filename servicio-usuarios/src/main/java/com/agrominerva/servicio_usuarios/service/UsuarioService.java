package com.agrominerva.servicio_usuarios.service;

import com.agrominerva.servicio_usuarios.entity.Usuario;
import com.agrominerva.servicio_usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    // 1. Inyección de Dependencias (solo las que necesitamos)
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // El Bean de SecurityConfig

    /**
     * Crea un nuevo usuario en la base de datos.
     */
    public Usuario crearUsuario(Usuario usuario) {
        // 1. Validar si el email ya existe
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            // Es mejor lanzar una excepción específica, pero RuntimeException funciona
            throw new RuntimeException("El email ya está en uso"); 
        }

        // 2. Hashear la contraseña antes de guardar [cite: 8]
        String passwordHasheada = passwordEncoder.encode(usuario.getPasswordHash());
        usuario.setPasswordHash(passwordHasheada);

        // 3. Guardar en la base de datos
        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        // 4. Lógica de Kafka ELIMINADA (como solicitaste)

        return nuevoUsuario;
    }

    /**
     * Valida las credenciales para el login [cite: 3]
     */
    public Optional<Usuario> validarLogin(String email, String passwordPlana) {
        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(email);

        // Verifica si el usuario existe y está activo [cite: 11]
        if (optUsuario.isPresent() && optUsuario.get().isActivo()) {
            Usuario usuario = optUsuario.get();
            
            // Comparamos la contraseña plana con el hash guardado
            if (passwordEncoder.matches(passwordPlana, usuario.getPasswordHash())) {
                return optUsuario; // ¡Login correcto!
            }
        }
        
        return Optional.empty(); // Falla el login (usuario no encontrado, inactivo o pass incorrecta)
    }
    
    public Optional<Usuario> getUsuarioById(UUID id) {
        return usuarioRepository.findById(id);
    }
    
    public Usuario updateUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }


}

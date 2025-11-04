package com.agrominerva.servicio_usuarios.controller;

import com.agrominerva.servicio_usuarios.dto.UsuarioRequestDTO;
import com.agrominerva.servicio_usuarios.dto.UsuarioResponseDTO;
import com.agrominerva.servicio_usuarios.dto.LoginRequestDTO;
import com.agrominerva.servicio_usuarios.entity.Usuario;
import com.agrominerva. servicio_usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para registrar un nuevo usuario (ADMIN o EMPLEADO)
     */
    @PostMapping("/registro") // Ruta: POST /api/v1/usuarios/registro
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioRequestDTO request) {
        try {
            // 1. Mapear DTO a Entidad
            Usuario usuario = new Usuario();
            usuario.setEmail(request.getEmail());
            usuario.setPasswordHash(request.getPassword()); // Pasamos la contra plana
            usuario.setNombre(request.getNombre());
            usuario.setRol(request.getRol());

            // 2. Llamar al servicio
            Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);

            // 3. Mapear Entidad a DTO de Respuesta (para no exponer el hash)
            UsuarioResponseDTO response = new UsuarioResponseDTO();
            response.setId(nuevoUsuario.getId());
            response.setEmail(nuevoUsuario.getEmail());
            response.setNombre(nuevoUsuario.getNombre());
            response.setRol(nuevoUsuario.getRol());
            response.setActivo(nuevoUsuario.isActivo());
            response.setCreatedAt(nuevoUsuario.getCreatedAt());

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            // Si el email ya existe, por ejemplo
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint para la autenticación [cite: 3]
     */
    @PostMapping("/login") // Ruta: POST /api/v1/usuarios/login
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        
        Optional<Usuario> usuarioValidado = usuarioService.validarLogin(
            request.getEmail(), 
            request.getPassword()
        );

        if (usuarioValidado.isPresent()) {
            // ¡Login exitoso!
            // TODO: En el siguiente paso, aquí generaríamos un token JWT
            
            // Por ahora, solo devolvemos un DTO de respuesta seguro
            Usuario usuario = usuarioValidado.get();
            UsuarioResponseDTO response = new UsuarioResponseDTO();
            response.setId(usuario.getId());
            response.setEmail(usuario.getEmail());
            response.setNombre(usuario.getNombre());
            response.setRol(usuario.getRol());
            response.setActivo(usuario.isActivo());
            response.setCreatedAt(usuario.getCreatedAt());

            return ResponseEntity.ok(response);
            
        } else {
            // Credenciales inválidas
            return new ResponseEntity<>("Email o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioById(@PathVariable("id") String id) {
        Optional<Usuario> usuarioOpt = usuarioService.getUsuarioById(java.util.UUID.fromString(id));
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            UsuarioResponseDTO response = new UsuarioResponseDTO();
            response.setId(usuario.getId());
            response.setEmail(usuario.getEmail());
            response.setNombre(usuario.getNombre());
            response.setRol(usuario.getRol());
            response.setActivo(usuario.isActivo());
            response.setCreatedAt(usuario.getCreatedAt());
            return ResponseEntity.ok(response);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable("id") String id, @RequestBody
                                                  UsuarioRequestDTO request) {
        Optional<Usuario> usuarioOpt = usuarioService.getUsuarioById(java.util.UUID.fromString(id));
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                usuario.setEmail(request.getEmail());
                usuario.setNombre(request.getNombre());
                usuario.setRol(request.getRol());
                // Nota: No actualizamos la contraseña aquí por simplicidad
    
                Usuario updatedUsuario = usuarioService.updateUsuario(usuario);
    
                UsuarioResponseDTO response = new UsuarioResponseDTO();
                response.setId(updatedUsuario.getId());
                response.setEmail(updatedUsuario.getEmail());
                response.setNombre(updatedUsuario.getNombre());
                response.setRol(updatedUsuario.getRol());
                response.setActivo(updatedUsuario.isActivo());
                response.setCreatedAt(updatedUsuario.getCreatedAt());
    
                return ResponseEntity.ok(response);
            } else {
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }  
}
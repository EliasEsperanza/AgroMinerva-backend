package com.agrominerva.servicio_usuarios.dto;

import com.agrominerva.servicio_usuarios.entity.Rol;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UsuarioResponseDTO {
    private UUID id;
    private String email;
    private String nombre;
    private Rol rol;
    private boolean activo;
    private LocalDateTime createdAt;
}

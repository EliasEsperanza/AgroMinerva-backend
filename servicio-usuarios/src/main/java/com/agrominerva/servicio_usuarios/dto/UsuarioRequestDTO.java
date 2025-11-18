package com.agrominerva.servicio_usuarios.dto;

import com.agrominerva.servicio_usuarios.entity.Rol;
import lombok.Data;

@Data
public class UsuarioRequestDTO {
    private String email;
    private String password; 
    private String nombre;
    private String telefono;
    private Rol rol;
}

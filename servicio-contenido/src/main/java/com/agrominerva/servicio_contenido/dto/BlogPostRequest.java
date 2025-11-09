package com.agrominerva.servicio_contenido.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostRequest {
    
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 500, message = "El título no puede exceder 500 caracteres")
    private String titulo;
    
    @NotBlank(message = "El contenido es obligatorio")
    private String contenido;
    
    @Size(max = 1000, message = "La URL de imagen no puede exceder 1000 caracteres")
    private String imagenPortadaUrl;
    
    private Boolean publicado = false;
}

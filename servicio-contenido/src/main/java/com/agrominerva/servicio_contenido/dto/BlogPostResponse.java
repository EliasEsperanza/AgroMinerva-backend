package com.agrominerva.servicio_contenido.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogPostResponse {
    
    private UUID id;
    private String titulo;
    private String slug;
    private String contenido;
    private String imagenPortadaUrl;
    private Boolean publicado;
    private LocalDateTime publicadoEn;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
}


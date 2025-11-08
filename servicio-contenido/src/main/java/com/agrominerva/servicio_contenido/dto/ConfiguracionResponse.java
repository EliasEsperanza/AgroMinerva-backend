package com.agrominerva.servicio_contenido.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracionResponse {
    
    private Integer id;
    private String clave;
    private String valor;
    private String descripcion;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
}
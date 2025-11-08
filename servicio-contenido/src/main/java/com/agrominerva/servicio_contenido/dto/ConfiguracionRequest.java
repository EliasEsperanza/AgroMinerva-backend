package com.agrominerva.servicio_contenido.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracionRequest {
    
    @NotBlank(message = "La clave es obligatoria")
    private String clave;
    
    @NotBlank(message = "El valor es obligatorio")
    private String valor;
    
    private String descripcion;
}

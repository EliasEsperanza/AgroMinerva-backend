package com.agrominerva.servicio_inventario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ProductoDTO {
    private UUID id;
    private String nombre;
    
    // Constructores
    public ProductoDTO() {}
    
    public ProductoDTO(UUID id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
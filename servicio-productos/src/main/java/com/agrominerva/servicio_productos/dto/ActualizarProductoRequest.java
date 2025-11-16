package com.agrominerva.servicio_productos.dto;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public class ActualizarProductoRequest {
    
    private String nombre;
    private String descripcion;
    
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal precio;
    
    private String unidad;
    private String imagenUrl;
    private UUID categoriaId;
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    
    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }
    
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
    
    public UUID getCategoriaId() { return categoriaId; }
    public void setCategoriaId(UUID categoriaId) { this.categoriaId = categoriaId; }
}
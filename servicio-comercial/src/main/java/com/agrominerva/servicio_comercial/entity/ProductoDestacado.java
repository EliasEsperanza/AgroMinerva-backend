package com.agrominerva.servicio_comercial.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "productos_destacados")
public class ProductoDestacado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "producto_id", nullable = false)
    private UUID productoId;
    
    @Column(nullable = false)
    private Integer orden = 0;
    
    // Constructores
    public ProductoDestacado() {}
    
    public ProductoDestacado(UUID productoId, Integer orden) {
        this.productoId = productoId;
        this.orden = orden;
    }
    
    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }
    
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
}
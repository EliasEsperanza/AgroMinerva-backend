package com.agrominerva.servicio_inventario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "inventario_stock")
public class InventarioStock {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "producto_id", unique = true, nullable = false)
    private UUID productoId;
    
    @Column(nullable = false)
    @NotNull(message = "El stock no puede ser nulo")
    private Integer stock = 0;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    // Constructores
    public InventarioStock() {
        this.updatedAt = LocalDateTime.now();
    }
    
    public InventarioStock(UUID productoId, Integer stock) {
        this();
        this.productoId = productoId;
        this.stock = stock != null ? stock : 0;
    }
    
    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }
    
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { 
        this.stock = stock != null ? stock : 0;
        this.updatedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    // Métodos de negocio
    public void aumentarStock(Integer cantidad) {
        if (cantidad != null && cantidad > 0) {
            this.stock += cantidad;
            this.updatedAt = LocalDateTime.now();
        }
    }
    
    public void disminuirStock(Integer cantidad) {
        if (cantidad != null && cantidad > 0) {
            if (this.stock >= cantidad) {
                this.stock -= cantidad;
                this.updatedAt = LocalDateTime.now();
            } else {
                throw new RuntimeException("Stock insuficiente. Stock actual: " + this.stock);
            }
        }
    }
}
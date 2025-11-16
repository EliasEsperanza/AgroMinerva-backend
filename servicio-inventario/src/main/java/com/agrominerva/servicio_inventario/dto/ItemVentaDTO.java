package com.agrominerva.servicio_inventario.dto;

import java.util.UUID;

public class ItemVentaDTO {
    private UUID productoId;
    private Integer cantidad;
    
    // Constructores
    public ItemVentaDTO() {}
    
    public ItemVentaDTO(UUID productoId, Integer cantidad) {
        this.productoId = productoId;
        this.cantidad = cantidad;
    }
    
    // Getters y Setters
    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }
    
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
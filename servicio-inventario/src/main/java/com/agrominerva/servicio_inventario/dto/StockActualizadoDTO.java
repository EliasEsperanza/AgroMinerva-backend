package com.agrominerva.servicio_inventario.dto;

import java.util.UUID;

public class StockActualizadoDTO {
    private UUID productoId;
    private Integer nuevoStock;
    
    // Constructores
    public StockActualizadoDTO() {}
    
    public StockActualizadoDTO(UUID productoId, Integer nuevoStock) {
        this.productoId = productoId;
        this.nuevoStock = nuevoStock;
    }
    
    // Getters y Setters
    public UUID getProductoId() { return productoId; }
    public void setProductoId(UUID productoId) { this.productoId = productoId; }
    
    public Integer getNuevoStock() { return nuevoStock; }
    public void setNuevoStock(Integer nuevoStock) { this.nuevoStock = nuevoStock; }
}
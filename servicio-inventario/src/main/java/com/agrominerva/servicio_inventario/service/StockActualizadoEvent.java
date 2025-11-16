package com.agrominerva.servicio_inventario.service;

import java.util.UUID;

public class StockActualizadoEvent {
    private final UUID productoId;
    private final Integer nuevoStock;

    public StockActualizadoEvent(UUID productoId, Integer nuevoStock) {
        this.productoId = productoId;
        this.nuevoStock = nuevoStock;
    }

    public UUID getProductoId() {
        return productoId;
    }

    public Integer getNuevoStock() {
        return nuevoStock;
    }
}
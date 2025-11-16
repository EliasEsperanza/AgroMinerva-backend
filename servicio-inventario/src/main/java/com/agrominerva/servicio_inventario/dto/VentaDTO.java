package com.agrominerva.servicio_inventario.dto;

import java.util.List;
import java.util.UUID;

public class VentaDTO {
    private UUID pedidoId;
    private List<ItemVentaDTO> items;
    
    // Constructores
    public VentaDTO() {}
    
    public VentaDTO(UUID pedidoId, List<ItemVentaDTO> items) {
        this.pedidoId = pedidoId;
        this.items = items;
    }
    
    // Getters y Setters
    public UUID getPedidoId() { return pedidoId; }
    public void setPedidoId(UUID pedidoId) { this.pedidoId = pedidoId; }
    
    public List<ItemVentaDTO> getItems() { return items; }
    public void setItems(List<ItemVentaDTO> items) { this.items = items; }
}
package com.agrominerva.servicio_ventas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class CrearPedidoRequest {
    @NotBlank
    private String clienteNombre;

    @NotNull
    private UUID creadoPorUsuarioId;

    @NotEmpty
    private List<ItemRequest> items;

    public static class ItemRequest {
        @NotNull
        private UUID productoId;
        @NotNull @Min(1)
        private Integer cantidad;
        @NotNull
        private BigDecimal precioUnitario;

        // getters/setters
        public UUID getProductoId() {
            return productoId;
        }
        public void setProductoId(UUID productoId) {
            this.productoId = productoId;
        }
        public Integer getCantidad() {
            return cantidad;
        }
        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }
        public BigDecimal getPrecioUnitario() {
            return precioUnitario;
        }
        public void setPrecioUnitario(BigDecimal precioUnitario) {
            this.precioUnitario = precioUnitario;
        }
        
    }

    // getters/setters

    public String getClienteNombre() {
        return clienteNombre;
    }
    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }
    public UUID getCreadoPorUsuarioId() {
        return creadoPorUsuarioId;
    }
    public void setCreadoPorUsuarioId(UUID creadoPorUsuarioId) {
        this.creadoPorUsuarioId = creadoPorUsuarioId;
    }
    public List<ItemRequest> getItems() {
        return items;
    }
    public void setItems(List<ItemRequest> items) {
        this.items = items;
    }

}

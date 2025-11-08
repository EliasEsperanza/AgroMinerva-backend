package com.agrominerva.servicio_ventas.dto;

import lombok.Data;
import java.util.List;
import java.util.UUID;



@Data
public class VentaDTO {
    private UUID pedidoId;
    private List<Item> items;
    public static class Item {
       private UUID productoId;
       private Integer cantidad;

        public UUID getProductoId() {
            return productoId;
        }
        public Integer getCantidad() {
            return cantidad;
        }

        public void setProductoId(UUID productoId) {
            this.productoId = productoId;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }
    }
    // getters/setters

    public UUID getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(UUID pedidoId) {
        this.pedidoId = pedidoId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


}


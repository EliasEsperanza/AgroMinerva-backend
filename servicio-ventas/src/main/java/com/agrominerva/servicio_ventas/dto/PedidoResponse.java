package com.agrominerva.servicio_ventas.dto;

import lombok.Data;

import com.agrominerva.servicio_ventas.model.Pedido;
import com.agrominerva.servicio_ventas.model.PedidoItem;
import java.util.ArrayList;
import java.util.List;

@Data
public class PedidoResponse {
    private String id;
    private String estado;
    private String fechaCreacion;
    private String fechaActualizacion;
    private VentaDTO venta;

    // getters/setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public String getFechaActualizacion() {
        return fechaActualizacion;
    }
    public void setFechaActualizacion(String fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    public VentaDTO getVenta() {
        return venta;
    }
    public void setVenta(VentaDTO venta) {
        this.venta = venta;
    }

    public static PedidoResponse fromEntity(Pedido pedido) {
        PedidoResponse response = new PedidoResponse();
        response.setId(pedido.getId().toString());
        response.setEstado(pedido.getEstado().name());
        response.setFechaCreacion(pedido.getCreatedAt().toString());
        response.setFechaActualizacion(pedido.getCreatedAt().toString());
        
        VentaDTO ventaDTO = new VentaDTO();
        ventaDTO.setPedidoId(pedido.getId());
        List<VentaDTO.Item> items = new ArrayList<>();
        for (PedidoItem item : pedido.getItems()) {
            VentaDTO.Item dtoItem = new VentaDTO.Item();
            dtoItem.setProductoId(item.getProductoId());
            dtoItem.setCantidad(item.getCantidad());
            items.add(dtoItem);
        }
        ventaDTO.setItems(items);
        response.setVenta(ventaDTO);

        return response;
    }

}

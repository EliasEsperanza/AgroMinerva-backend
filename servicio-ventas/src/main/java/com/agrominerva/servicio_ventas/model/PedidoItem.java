package com.agrominerva.servicio_ventas.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "pedidos_items")
public class PedidoItem {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Column(name = "producto_id", nullable = false)
    private UUID productoId;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario_congelado", nullable = false)
    private BigDecimal precioUnitarioCongelado;

    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
    }
    // getters/setters

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public Pedido getPedido() {
        return pedido;
    }
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
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
    public BigDecimal getPrecioUnitarioCongelado() {
        return precioUnitarioCongelado;
    }
    public void setPrecioUnitarioCongelado(BigDecimal precioUnitarioCongelado) {
        this.precioUnitarioCongelado = precioUnitarioCongelado;
    }

}

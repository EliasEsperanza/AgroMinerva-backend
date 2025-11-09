package com.agrominerva.servicio_ventas.service;

import com.agrominerva.servicio_ventas.model.EstadoPedido;
import com.agrominerva.servicio_ventas.model.Pedido;
import com.agrominerva.servicio_ventas.model.PedidoItem;
import com.agrominerva.servicio_ventas.repository.PedidoRepository;
import com.agrominerva.servicio_ventas.dto.CrearPedidoRequest;
import com.agrominerva.servicio_ventas.dto.VentaDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class PedidoService {
    private final PedidoRepository pedidoRepo;
    private final KafkaTemplate<String, Object> kafka;

    public PedidoService(PedidoRepository pedidoRepo, KafkaTemplate<String, Object> kafka) {
        this.pedidoRepo = pedidoRepo;
        this.kafka = kafka;
    }

    @Transactional
    public Pedido crearPedido(CrearPedidoRequest req) {
        Pedido p = new Pedido();
        p.setClienteNombre(req.getClienteNombre());
        p.setCreadoPorUsuarioId(req.getCreadoPorUsuarioId());
        p.setEstado(EstadoPedido.PENDIENTE);

        BigDecimal total = BigDecimal.ZERO;
        for (var it : req.getItems()) {
            PedidoItem item = new PedidoItem();
            item.setPedido(p);
            item.setProductoId(it.getProductoId());
            item.setCantidad(it.getCantidad());
            item.setPrecioUnitarioCongelado(it.getPrecioUnitario());
            p.getItems().add(item);
            total = total.add(it.getPrecioUnitario().multiply(BigDecimal.valueOf(it.getCantidad())));
        }
        p.setTotalVenta(total);
        Pedido guardado = pedidoRepo.save(p);

        // Emitir evento (solo producto_id y cantidad)
        VentaDTO venta = new VentaDTO();
        venta.setPedidoId(guardado.getId());
        venta.setItems(guardado.getItems().stream()
           .map(i -> { var it = new VentaDTO.Item(); it.setProductoId(i.getProductoId()); it.setCantidad(i.getCantidad()); return it;})
           .collect(Collectors.toList())
        );

        kafka.send("topic-venta-registrada", venta);
        return guardado;
    }

    @Transactional
    public Pedido cambiarEstado(UUID pedidoId, EstadoPedido nuevoEstado) {
        Pedido p = pedidoRepo.findById(pedidoId).orElseThrow(() -> new EntityNotFoundException("Pedido no existe"));
        p.setEstado(nuevoEstado);
        return pedidoRepo.save(p);
    }
}


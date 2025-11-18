package com.agrominerva.servicio_inventario.cloudstream;

import com.agrominerva.servicio_inventario.dto.ProductoDTO;
import com.agrominerva.servicio_inventario.dto.VentaDTO;
import com.agrominerva.servicio_inventario.service.InventarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Configuration
public class CloudStreamConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(CloudStreamConsumerService.class);

    @Autowired
    private InventarioService inventarioService;

    @Bean
    public Consumer<ProductoDTO> manejarProductoCreado() {
        return productoDTO -> {
            try {
                logger.info("Recibido evento producto-creado via Cloud Stream: {}", productoDTO.getId());
                inventarioService.crearRegistroStock(productoDTO.getId());
                logger.info("Registro de stock creado para producto: {}", productoDTO.getId());
            } catch (Exception e) {
                logger.error("Error procesando producto-creado: {}", e.getMessage());
                // Aquí podrías implementar una lógica para enviar a una dead letter queue (DLQ)
            }
        };
    }

    @Bean
    public Consumer<Map<String, String>> manejarProductoEliminado() {
        return payload -> {
            try {
                UUID productoId = UUID.fromString(payload.get("producto_id"));
                logger.info("Recibido evento producto-eliminado via Cloud Stream: {}", productoId);
                inventarioService.eliminarRegistroStock(productoId);
                logger.info("Registro de stock eliminado para producto: {}", productoId);
            } catch (Exception e) {
                logger.error("Error procesando producto-eliminado: {}", e.getMessage());
            }
        };
    }

    @Bean
    public Consumer<VentaDTO> manejarVentaRegistrada() {
        return ventaDTO -> {
            try {
                logger.info("Recibido evento venta-registrada via Cloud Stream. Pedido: {}", ventaDTO.getPedidoId());
                inventarioService.procesarVenta(ventaDTO);
                logger.info("Stock actualizado para venta del pedido: {}", ventaDTO.getPedidoId());
            } catch (Exception e) {
                logger.error("Error procesando venta-registrada: {}", e.getMessage());
                // Re-lanzar la excepción para que el binder de Spring Cloud Stream gestione el reintento.
                throw new RuntimeException(e);
            }
        };
    }
}

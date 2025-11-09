package com.agrominerva.servicio_inventario.service;

import com.agrominerva.servicio_inventario.dto.ProductoDTO;
import com.agrominerva.servicio_inventario.dto.VentaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class KafkaConsumerService {
    
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
    
    @Autowired
    private InventarioService inventarioService;
    
    @KafkaListener(topics = "topic-producto-creado", groupId = "inventario-group")
    public void manejarProductoCreado(ProductoDTO productoDTO) {
        try {
            logger.info("Recibido evento producto-creado: {}", productoDTO.getId());
            inventarioService.crearRegistroStock(productoDTO.getId());
            logger.info("Registro de stock creado para producto: {}", productoDTO.getId());
        } catch (Exception e) {
            logger.error("Error procesando producto-creado: {}", e.getMessage());
            // Aquí podrías implementar dead letter queue
        }
    }
    
    @KafkaListener(topics = "topic-producto-eliminado", groupId = "inventario-group")
    public void manejarProductoEliminado(Map<String, String> payload) {
        try {
            UUID productoId = UUID.fromString(payload.get("producto_id"));
            logger.info("Recibido evento producto-eliminado: {}", productoId);
            inventarioService.eliminarRegistroStock(productoId);
            logger.info("Registro de stock eliminado para producto: {}", productoId);
        } catch (Exception e) {
            logger.error("Error procesando producto-eliminado: {}", e.getMessage());
        }
    }
    
    @KafkaListener(topics = "topic-venta-registrada", groupId = "inventario-group")
    public void manejarVentaRegistrada(VentaDTO ventaDTO) {
        try {
            logger.info("Recibido evento venta-registrada. Pedido: {}", ventaDTO.getPedidoId());
            inventarioService.procesarVenta(ventaDTO);
            logger.info("Stock actualizado para venta del pedido: {}", ventaDTO.getPedidoId());
        } catch (Exception e) {
            logger.error("Error procesando venta-registrada: {}", e.getMessage());
            // IMPORTANTE: En caso de error, no confirmar el offset para reintentar
            throw e;
        }
    }
}
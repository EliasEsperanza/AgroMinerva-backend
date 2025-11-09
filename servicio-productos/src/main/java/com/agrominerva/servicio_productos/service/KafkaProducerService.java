package com.agrominerva.servicio_productos.service;

import com.agrominerva.servicio_productos.dto.ProductoDTO;
import com.agrominerva.servicio_productos.entity.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaProducerService {
    
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    public void publicarProductoCreado(Producto producto) {
        try {
            ProductoDTO productoDTO = new ProductoDTO(producto.getId(), producto.getNombre());
            kafkaTemplate.send("topic-producto-creado", productoDTO);
            logger.info("Evento producto-creado publicado: {}", productoDTO);
        } catch (Exception e) {
            logger.error("Error publicando producto-creado: {}", e.getMessage());
        }
    }
    
    public void publicarProductoActualizado(Producto producto) {
        try {
            ProductoDTO productoDTO = new ProductoDTO(producto.getId(), producto.getNombre());
            kafkaTemplate.send("topic-producto-actualizado", productoDTO);
            logger.info("Evento producto-actualizado publicado: {}", productoDTO);
        } catch (Exception e) {
            logger.error("Error publicando producto-actualizado: {}", e.getMessage());
        }
    }
    
    public void publicarProductoEliminado(UUID productoId) {
        try {
            kafkaTemplate.send("topic-producto-eliminado", 
                java.util.Map.of("producto_id", productoId.toString()));
            logger.info("Evento producto-eliminado publicado para producto: {}", productoId);
        } catch (Exception e) {
            logger.error("Error publicando producto-eliminado: {}", e.getMessage());
        }
    }
}
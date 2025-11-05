package com.agrominerva.servicio_inventario.service;

import com.agrominerva.servicio_inventario.dto.StockActualizadoDTO;
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
    
    public void publicarStockActualizado(UUID productoId, Integer nuevoStock) {
        try {
            StockActualizadoDTO stockDTO = new StockActualizadoDTO(productoId, nuevoStock);
            kafkaTemplate.send("topic-stock-actualizado", stockDTO);
            logger.info("Evento stock-actualizado publicado para producto: {}, stock: {}", productoId, nuevoStock);
        } catch (Exception e) {
            logger.error("Error publicando stock-actualizado: {}", e.getMessage());
        }
    }
}
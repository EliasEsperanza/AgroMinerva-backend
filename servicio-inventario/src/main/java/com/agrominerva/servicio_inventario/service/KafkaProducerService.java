package com.agrominerva.servicio_inventario.service;

import com.agrominerva.servicio_inventario.dto.StockActualizadoDTO;
import com.agrominerva.servicio_inventario.event.StockActualizadoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.UUID;

@Service
public class KafkaProducerService {
    
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleStockActualizadoEvent(StockActualizadoEvent event) {
        UUID productoId = event.getProductoId();
        Integer nuevoStock = event.getNuevoStock();
        
        try {
            StockActualizadoDTO stockDTO = new StockActualizadoDTO(productoId, nuevoStock);
            kafkaTemplate.send("topic-stock-actualizado", stockDTO);
            logger.info("Evento stock-actualizado publicado para producto: {}, stock: {}", productoId, nuevoStock);
        } catch (Exception e) {
            logger.error("Error publicando evento 'stock-actualizado' para producto {}: {}", productoId, e.getMessage());
        }
    }
}
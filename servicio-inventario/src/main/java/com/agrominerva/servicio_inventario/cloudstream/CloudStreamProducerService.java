package com.agrominerva.servicio_inventario.cloudstream;

import com.agrominerva.servicio_inventario.dto.StockActualizadoDTO;
import com.agrominerva.servicio_inventario.service.StockActualizadoEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class CloudStreamProducerService {
    
    private static final Logger logger = LoggerFactory.getLogger(CloudStreamProducerService.class);
    
    // Inyectamos RestTemplate para hacer llamadas HTTP
    @Autowired
    private RestTemplate restTemplate;

    // Inyectamos la URL del servicio de destino desde application.yaml
    @Value("${services.notificaciones.url}/stock/actualizar")
    private String notificationUrl;
    
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleStockActualizadoEvent(StockActualizadoEvent event) {
        UUID productoId = event.getProductoId();
        Integer nuevoStock = event.getNuevoStock();
        StockActualizadoDTO stockDTO = new StockActualizadoDTO(productoId, nuevoStock);

        try {
            logger.info("Enviando actualización de stock por HTTP a: {}", notificationUrl);
            restTemplate.postForEntity(notificationUrl, stockDTO, Void.class);
            logger.info("Notificación HTTP de stock actualizado enviada para producto: {}, stock: {}", productoId, nuevoStock);
        } catch (Exception e) {
            logger.error("Error enviando notificación HTTP para producto {}: {}", productoId, e.getMessage());
        }
    }
}
package com.agrominerva.servicio_inventario.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

/**
 * Cliente HTTP para comunicarse con el servicio-productos
 */
@Component
public class ProductosClient {
    
    private static final Logger log = LoggerFactory.getLogger(ProductosClient.class);
    
    private final RestTemplate restTemplate;
    private final String productosServiceUrl;

    public ProductosClient(
            RestTemplate restTemplate,
            @Value("${app.servicios.productos.url}") String productosServiceUrl) {
        this.restTemplate = restTemplate;
        this.productosServiceUrl = productosServiceUrl;
    }

    /**
     * Notifica al servicio de productos sobre cambios en el stock
     */
    public void notificarStockActualizado(UUID productoId, Integer nuevoStock) {
        try {
            String url = productosServiceUrl + "/api/productos/stock-actualizado";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, Object> payload = Map.of(
                "productoId", productoId.toString(),
                "nuevoStock", nuevoStock
            );
            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            
            restTemplate.postForEntity(url, request, Void.class);
            
            log.info("Stock actualizado notificado al servicio de productos. Producto ID: {}, Stock: {}", 
                    productoId, nuevoStock);
            
        } catch (Exception e) {
            log.error("Error al notificar stock actualizado al servicio de productos. Producto ID: {}, Error: {}", 
                    productoId, e.getMessage());
        }
    }
}
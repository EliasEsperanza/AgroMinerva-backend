package com.agrominerva.servicio_productos.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

/**
 * Cliente HTTP para comunicarse con el servicio-inventario
 */
@Component
public class InventarioClient {
    
    private static final Logger log = LoggerFactory.getLogger(InventarioClient.class);
    
    private final RestTemplate restTemplate;
    private final String inventarioServiceUrl;

    public InventarioClient(
            RestTemplate restTemplate,
            @Value("${app.servicios.inventario.url}") String inventarioServiceUrl) {
        this.restTemplate = restTemplate;
        this.inventarioServiceUrl = inventarioServiceUrl;
    }

    /**
     * Notifica al servicio de inventario que se creó un producto
     * El inventario debe crear un registro con stock = 0
     */
    public void notificarProductoCreado(UUID productoId, String nombre) {
        try {
            String url = inventarioServiceUrl + "/api/inventario/producto-creado";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, Object> payload = Map.of(
                "productoId", productoId.toString(),
                "nombre", nombre
            );
            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            
            restTemplate.postForEntity(url, request, Void.class);
            
            log.info("Producto creado notificado al servicio de inventario. Producto ID: {}", productoId);
            
        } catch (Exception e) {
            log.error("Error al notificar producto creado al servicio de inventario. Producto ID: {}. Error: {}", 
                    productoId, e.getMessage());
        }
    }

    /**
     * Notifica al servicio de inventario que se actualizó un producto
     * Por si necesitan actualizar algún dato en el futuro
     */
    public void notificarProductoActualizado(UUID productoId, String nombre) {
        try {
            String url = inventarioServiceUrl + "/api/inventario/producto-actualizado";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, Object> payload = Map.of(
                "productoId", productoId.toString(),
                "nombre", nombre
            );
            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            
            restTemplate.postForEntity(url, request, Void.class);
            
            log.info("Producto actualizado notificado al servicio de inventario. Producto ID: {}", productoId);
            
        } catch (Exception e) {
            log.error("Error al notificar producto actualizado al servicio de inventario. Producto ID: {}. Error: {}", 
                    productoId, e.getMessage());
        }
    }

    /**
     * Notifica al servicio de inventario que se eliminó un producto
     * El inventario debe eliminar el registro de stock correspondiente
     */
    public void notificarProductoEliminado(UUID productoId) {
        try {
            String url = inventarioServiceUrl + "/api/inventario/producto-eliminado";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, Object> payload = Map.of(
                "productoId", productoId.toString()
            );
            
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);
            
            restTemplate.postForEntity(url, request, Void.class);
            
            log.info("Producto eliminado notificado al servicio de inventario. Producto ID: {}", productoId);
            
        } catch (Exception e) {
            log.error("Error al notificar producto eliminado al servicio de inventario. Producto ID: {}. Error: {}", 
                    productoId, e.getMessage());
        }
    }
}
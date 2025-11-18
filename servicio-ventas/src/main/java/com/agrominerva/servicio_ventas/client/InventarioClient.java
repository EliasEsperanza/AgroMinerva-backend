package com.agrominerva.servicio_ventas.client;

import com.agrominerva.servicio_ventas.dto.VentaDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
     * Notifica al servicio de inventario que se registró una venta
     * @param ventaDTO datos de la venta (pedido_id, items con producto_id y cantidad)
     */
    public void notificarVentaRegistrada(VentaDTO ventaDTO) {
        try {
            String url = inventarioServiceUrl + "/api/inventario/registrar-venta";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<VentaDTO> request = new HttpEntity<>(ventaDTO, headers);
            
            restTemplate.postForEntity(url, request, Void.class);
            
            log.info("Venta notificada al servicio de inventario. Pedido ID: {}", ventaDTO.getPedidoId());
            
        } catch (Exception e) {
            // NO lanzamos excepción para no fallar la venta si inventario está caído
            log.error("Error al notificar venta al servicio de inventario. Pedido ID: {}. Error: {}", 
                    ventaDTO.getPedidoId(), e.getMessage());
        }
    }
}
package com.agrominerva.servicio_inventario.controller;

import com.agrominerva.servicio_inventario.service.InventarioService;
import com.agrominerva.servicio_inventario.dto.ProductoDTO;
import com.agrominerva.servicio_inventario.dto.VentaDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador HTTP para recibir eventos de otros servicios
 * Reemplaza los consumers de Kafka
 */
@RestController
@RequestMapping("/inventario/eventos")
public class InventarioEventController {
    
    private static final Logger log = LoggerFactory.getLogger(InventarioEventController.class);
    
    private final InventarioService inventarioService;

    public InventarioEventController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    /**
     * Endpoint para recibir evento de producto creado (reemplaza topic-producto-creado)
     */
    @PostMapping("/producto-creado")
    public ResponseEntity<Void> handleProductoCreado(@RequestBody ProductoDTO request) {
        try {
            log.info("Recibido evento HTTP: producto creado - ID: {}, Nombre: {}", 
                    request.getId(), request.getNombre());
            
            inventarioService.crearRegistroStock(request.getId());
            
            return ResponseEntity.ok().build();
            
        } catch (Exception e) {
            log.error("Error procesando producto creado: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Endpoint para recibir evento de producto eliminado (reemplaza topic-producto-eliminado)
     */
    @PostMapping("/producto-eliminado")
    public ResponseEntity<Void> handleProductoEliminado(@RequestBody ProductoDTO request) {
        try {
            log.info("Recibido evento HTTP: producto eliminado - ID: {}", request.getId());
            
            inventarioService.eliminarRegistroStock(request.getId());
            
            return ResponseEntity.ok().build();
            
        } catch (Exception e) {
            log.error("Error procesando producto eliminado: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Endpoint para recibir evento de venta registrada (reemplaza topic-venta-registrada)
     */
    @PostMapping("/venta-registrada")
    public ResponseEntity<Void> handleVentaRegistrada(@RequestBody VentaDTO request) {
        try {
            log.info("Recibido evento HTTP: venta registrada - Venta ID: {}, Productos: {}", 
                    request.getPedidoId(), request.getItems().size());
            
            inventarioService.procesarVenta(request);
            
            return ResponseEntity.ok().build();
            
            
        } catch (Exception e) {
            log.error("Error procesando venta registrada: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
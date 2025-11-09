package com.agrominerva.servicio_inventario.service;

import com.agrominerva.servicio_inventario.dto.VentaDTO;
import com.agrominerva.servicio_inventario.event.StockActualizadoEvent;
import com.agrominerva.servicio_inventario.entity.InventarioStock;
import com.agrominerva.servicio_inventario.repository.InventarioStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.ApplicationEventPublisher;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InventarioService {
    
    @Autowired
    private InventarioStockRepository inventarioRepository;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    public List<InventarioStock> findAll() {
        return inventarioRepository.findAll();
    }
    
    public Optional<InventarioStock> findByProductoId(UUID productoId) {
        return inventarioRepository.findByProductoId(productoId);
    }
    
    public Optional<InventarioStock> findById(UUID id) {
        return inventarioRepository.findById(id);
    }
    
    @Transactional
    public InventarioStock crearRegistroStock(UUID productoId) {
        // Verificar si ya existe
        if (inventarioRepository.existsByProductoId(productoId)) {
            throw new RuntimeException("Ya existe un registro de stock para el producto: " + productoId);
        }
        
        InventarioStock nuevoStock = new InventarioStock(productoId, 0);
        InventarioStock saved = inventarioRepository.save(nuevoStock);
        
        // Publicar evento para ser manejado asíncronamente
        eventPublisher.publishEvent(new StockActualizadoEvent(productoId, 0));
        
        return saved;
    }
    
    @Transactional
    public InventarioStock actualizarStock(UUID productoId, Integer nuevoStock) {
        InventarioStock inventario = inventarioRepository.findByProductoId(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado en inventario: " + productoId));
        
        inventario.setStock(nuevoStock);
        InventarioStock updated = inventarioRepository.save(inventario);
        
        // Publicar evento para ser manejado asíncronamente
        eventPublisher.publishEvent(new StockActualizadoEvent(productoId, nuevoStock));
        
        return updated;
    }
    
    @Transactional
    public InventarioStock aumentarStock(UUID productoId, Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a aumentar debe ser un número positivo.");
        }
        
        InventarioStock inventario = inventarioRepository.findByProductoId(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado en inventario: " + productoId));
        
        inventario.aumentarStock(cantidad);
        InventarioStock updated = inventarioRepository.save(inventario);
        
        // Publicar evento para ser manejado asíncronamente
        eventPublisher.publishEvent(new StockActualizadoEvent(productoId, updated.getStock()));
        
        return updated;
    }
    
    @Transactional
    public void eliminarRegistroStock(UUID productoId) {
        inventarioRepository.deleteByProductoId(productoId);
    }
    
    @Transactional
    public void procesarVenta(VentaDTO ventaDTO) {
        for (var item : ventaDTO.getItems()) {
            UUID productoId = item.getProductoId();
            Integer cantidad = item.getCantidad();
            
            if (cantidad == null || cantidad <= 0) {
                throw new RuntimeException("Cantidad inválida para el producto: " + productoId);
            }
            
            InventarioStock inventario = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado en inventario: " + productoId));
            
            try {
                inventario.disminuirStock(cantidad);
                inventarioRepository.save(inventario);
                
                // Publicar evento para ser manejado asíncronamente
                eventPublisher.publishEvent(new StockActualizadoEvent(productoId, inventario.getStock()));
                
            } catch (RuntimeException e) {
                throw new RuntimeException("Error procesando venta para producto " + productoId + ": " + e.getMessage());
            }
        }
    }
    
    public Integer consultarStock(UUID productoId) {
        return inventarioRepository.findByProductoId(productoId)
            .map(InventarioStock::getStock)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado en inventario: " + productoId));
    }
}
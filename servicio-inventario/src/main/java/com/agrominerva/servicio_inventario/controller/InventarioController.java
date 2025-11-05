package com.agrominerva.servicio_inventario.controller;

import com.agrominerva.servicio_inventario.entity.InventarioStock;
import com.agrominerva.servicio_inventario.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "*")
public class InventarioController {
    
    @Autowired
    private InventarioService inventarioService;
    
    @GetMapping
    public List<InventarioStock> getAllInventario() {
        return inventarioService.findAll();
    }
    
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<Integer> getStockByProductoId(@PathVariable UUID productoId) {
        try {
            Integer stock = inventarioService.consultarStock(productoId);
            return ResponseEntity.ok(stock);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<InventarioStock> getInventarioById(@PathVariable UUID id) {
        return inventarioService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/producto/{productoId}")
    public ResponseEntity<InventarioStock> crearRegistroStock(@PathVariable UUID productoId) {
        try {
            InventarioStock inventario = inventarioService.crearRegistroStock(productoId);
            return ResponseEntity.ok(inventario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/producto/{productoId}/stock")
    public ResponseEntity<InventarioStock> actualizarStock(
            @PathVariable UUID productoId,
            @RequestParam Integer stock) {
        try {
            InventarioStock inventario = inventarioService.actualizarStock(productoId, stock);
            return ResponseEntity.ok(inventario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/producto/{productoId}/aumentar")
    public ResponseEntity<InventarioStock> aumentarStock(
            @PathVariable UUID productoId,
            @RequestParam Integer cantidad) {
        try {
            InventarioStock inventario = inventarioService.aumentarStock(productoId, cantidad);
            return ResponseEntity.ok(inventario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/producto/{productoId}")
    public ResponseEntity<Void> eliminarRegistroStock(@PathVariable UUID productoId) {
        try {
            inventarioService.eliminarRegistroStock(productoId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
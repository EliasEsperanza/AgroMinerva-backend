package com.agrominerva.servicio_comercial.controller;

import com.agrominerva.servicio_comercial.entity.ProductoDestacado;
import com.agrominerva.servicio_comercial.service.ProductoDestacadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/productos-destacados")
@CrossOrigin(origins = "*")
public class ProductoDestacadoController {
    
    @Autowired
    private ProductoDestacadoService productoDestacadoService;
    
    @GetMapping
    public List<ProductoDestacado> getAllProductosDestacados() {
        return productoDestacadoService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDestacado> getProductoDestacadoById(@PathVariable UUID id) {
        return productoDestacadoService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> createProductoDestacado(@RequestBody ProductoDestacado productoDestacado) {
        try {
            ProductoDestacado saved = productoDestacadoService.save(productoDestacado);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}/orden")
    public ResponseEntity<ProductoDestacado> updateOrden(
            @PathVariable UUID id, 
            @RequestParam Integer orden) {
        try {
            ProductoDestacado updated = productoDestacadoService.updateOrden(id, orden);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductoDestacado(@PathVariable UUID id) {
        productoDestacadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/por-producto/{productoId}")
    public ResponseEntity<Void> deleteByProductoId(@PathVariable UUID productoId) {
        productoDestacadoService.deleteByProductoId(productoId);
        return ResponseEntity.noContent().build();
    }
}
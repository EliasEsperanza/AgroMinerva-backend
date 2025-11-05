package com.agrominerva.servicio_productos.controller;

import com.agrominerva.servicio_productos.dto.CrearProductoRequest;
import com.agrominerva.servicio_productos.entity.Producto;
import com.agrominerva.servicio_productos.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable UUID id) {
        return productoService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/categoria/{categoriaId}")
    public List<Producto> getProductosByCategoria(@PathVariable UUID categoriaId) {
        return productoService.findByCategoria_Id(categoriaId);
    }
    
    @GetMapping("/buscar")
    public List<Producto> buscarProductos(@RequestParam String nombre) {
        return productoService.buscarPorNombre(nombre);
    }
    
    @PostMapping
    public ResponseEntity<?> createProducto(@Valid @RequestBody CrearProductoRequest request) {
        try {
            Producto saved = productoService.crearProducto(request);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProducto(@PathVariable UUID id, @Valid @RequestBody CrearProductoRequest request) {
        try {
            Producto updated = productoService.actualizarProducto(id, request);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProducto(@PathVariable UUID id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
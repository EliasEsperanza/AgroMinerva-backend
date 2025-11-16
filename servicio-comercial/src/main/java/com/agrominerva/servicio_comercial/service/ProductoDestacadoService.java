package com.agrominerva.servicio_comercial.service;

import com.agrominerva.servicio_comercial.entity.ProductoDestacado;
import com.agrominerva.servicio_comercial.repository.ProductoDestacadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductoDestacadoService {
    
    @Autowired
    private ProductoDestacadoRepository productoDestacadoRepository;
    
    public List<ProductoDestacado> findAll() {
        return productoDestacadoRepository.findAllByOrderByOrdenAsc();
    }
    
    public Optional<ProductoDestacado> findById(UUID id) {
        return productoDestacadoRepository.findById(id);
    }
    
    public ProductoDestacado save(ProductoDestacado productoDestacado) {
        // Validar que el producto no esté ya destacado
        if (productoDestacadoRepository.existsByProductoId(productoDestacado.getProductoId())) {
            throw new RuntimeException("El producto ya está en la lista de destacados");
        }
        
        if (productoDestacado.getOrden() == null) {
            Integer maxOrden = productoDestacadoRepository.findMaxOrden();
            productoDestacado.setOrden(maxOrden != null ? maxOrden + 1 : 0);
        }
        
        return productoDestacadoRepository.save(productoDestacado);
    }
    
    public void deleteById(UUID id) {
        productoDestacadoRepository.deleteById(id);
    }
    
    public void deleteByProductoId(UUID productoId) {
    productoDestacadoRepository.findByProductoId(productoId)
        .ifPresent(productoDestacadoRepository::delete);
}
    
    public ProductoDestacado updateOrden(UUID id, Integer nuevoOrden) {
        return productoDestacadoRepository.findById(id)
            .map(productoDestacado -> {
                productoDestacado.setOrden(nuevoOrden);
                return productoDestacadoRepository.save(productoDestacado);
            })
            .orElseThrow(() -> new RuntimeException("Producto destacado no encontrado con id: " + id));
    }
}
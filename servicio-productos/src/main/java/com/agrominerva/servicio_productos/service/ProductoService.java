package com.agrominerva.servicio_productos.service;

import com.agrominerva.servicio_productos.client.InventarioClient;
import com.agrominerva.servicio_productos.dto.CrearProductoRequest;
import com.agrominerva.servicio_productos.entity.Categoria;
import com.agrominerva.servicio_productos.entity.Producto;
import com.agrominerva.servicio_productos.repository.CategoriaRepository;
import com.agrominerva.servicio_productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private InventarioClient inventarioClient;
    
    public List<Producto> findAll() {
        return productoRepository.findAllWithCategoria();
    }
    
    public Optional<Producto> findById(UUID id) {
        return productoRepository.findByIdWithCategoria(id);
    }
    
    public List<Producto> findByCategoria_Id(UUID categoriaId) {
        return productoRepository.findByCategoriaIdWithCategoria(categoriaId);
    }
    
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    @Transactional
    public Producto crearProducto(CrearProductoRequest request) {
        // Validar que la categoría existe
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + request.getCategoriaId()));
        
        // Validar que no exista un producto con el mismo nombre en la misma categoría
        if (productoRepository.existsByNombreAndCategoria_Id(request.getNombre(), request.getCategoriaId())) {
            throw new RuntimeException("Ya existe un producto con el nombre '" + request.getNombre() + 
                                     "' en la categoría '" + categoria.getNombre() + "'");
        }
        
        Producto producto = new Producto(
            request.getNombre(),
            request.getDescripcion(),
            request.getPrecio(),
            request.getUnidad(),
            request.getImagenUrl(),
            categoria
        );
        
        Producto savedProducto = productoRepository.save(producto);
        
        // Notificar al servicio de inventario vía HTTP
        inventarioClient.notificarProductoCreado(savedProducto.getId(), savedProducto.getNombre());
        
        return savedProducto;
    }
    
    @Transactional
    public Producto actualizarProducto(UUID id, CrearProductoRequest request) {
        return productoRepository.findById(id)
            .map(producto -> {
                // Validar categoría si se está actualizando
                Categoria categoria = producto.getCategoria();
                if (request.getCategoriaId() != null && 
                    !request.getCategoriaId().equals(producto.getCategoriaId())) {
                    categoria = categoriaRepository.findById(request.getCategoriaId())
                        .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + request.getCategoriaId()));
                }
                
                // Validar nombre único en la categoría si se está actualizando
                if (request.getNombre() != null && 
                    !request.getNombre().equals(producto.getNombre()) &&
                    productoRepository.existsByNombreAndCategoria_Id(request.getNombre(), categoria.getId())) {
                    throw new RuntimeException("Ya existe un producto con el nombre '" + request.getNombre() + 
                                             "' en la categoría '" + categoria.getNombre() + "'");
                }
                
                // Actualizar campos
                if (request.getNombre() != null) {
                    producto.setNombre(request.getNombre());
                }
                if (request.getDescripcion() != null) {
                    producto.setDescripcion(request.getDescripcion());
                }
                if (request.getPrecio() != null) {
                    producto.setPrecio(request.getPrecio());
                }
                if (request.getUnidad() != null) {
                    producto.setUnidad(request.getUnidad());
                }
                if (request.getImagenUrl() != null) {
                    producto.setImagenUrl(request.getImagenUrl());
                }
                if (categoria != producto.getCategoria()) {
                    producto.setCategoria(categoria);
                }
                
                Producto updatedProducto = productoRepository.save(producto);
                
                // Notificar al servicio de inventario vía HTTP
                inventarioClient.notificarProductoActualizado(updatedProducto.getId(), updatedProducto.getNombre());
                
                return updatedProducto;
            })
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }
    
    @Transactional
    public void eliminarProducto(UUID id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        
        productoRepository.deleteById(id);
        
        // Notificar al servicio de inventario vía HTTP
        inventarioClient.notificarProductoEliminado(id);
    }
    
    public boolean existsById(UUID id) {
        return productoRepository.existsById(id);
    }
}
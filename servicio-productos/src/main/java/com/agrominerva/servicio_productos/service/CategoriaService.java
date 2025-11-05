package com.agrominerva.servicio_productos.service;

import com.agrominerva.servicio_productos.entity.Categoria;
import com.agrominerva.servicio_productos.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }
    
    public List<Categoria> findAllWithProductos() {
        return categoriaRepository.findAllWithProductos();
    }
    
    public Optional<Categoria> findById(UUID id) {
        return categoriaRepository.findById(id);
    }
    
    public Optional<Categoria> findByNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }
    
    public Categoria save(Categoria categoria) {
        // Validar que no exista una categoría con el mismo nombre
        if (categoriaRepository.existsByNombre(categoria.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con el nombre: " + categoria.getNombre());
        }
        return categoriaRepository.save(categoria);
    }
    
    public Categoria update(UUID id, Categoria categoriaDetails) {
        return categoriaRepository.findById(id)
            .map(categoria -> {
                // Validar que el nuevo nombre no exista en otra categoría
                if (!categoria.getNombre().equals(categoriaDetails.getNombre()) &&
                    categoriaRepository.existsByNombre(categoriaDetails.getNombre())) {
                    throw new RuntimeException("Ya existe una categoría con el nombre: " + categoriaDetails.getNombre());
                }
                
                categoria.setNombre(categoriaDetails.getNombre());
                categoria.setDescripcion(categoriaDetails.getDescripcion());
                return categoriaRepository.save(categoria);
            })
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));
    }
    
    public void deleteById(UUID id) {
        Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));
        
        // Validar que la categoría no tenga productos asociados
        if (!categoria.getProductos().isEmpty()) {
            throw new RuntimeException("No se puede eliminar la categoría porque tiene productos asociados");
        }
        
        categoriaRepository.deleteById(id);
    }
    
    public List<Categoria> buscarPorNombre(String nombre) {
        return categoriaRepository.findByNombreContainingIgnoreCase(nombre);
    }
}
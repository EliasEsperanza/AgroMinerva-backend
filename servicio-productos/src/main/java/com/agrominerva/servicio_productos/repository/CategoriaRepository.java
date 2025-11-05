package com.agrominerva.servicio_productos.repository;

import com.agrominerva.servicio_productos.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
    
    Optional<Categoria> findByNombre(String nombre);
    
    boolean existsByNombre(String nombre);
    
    @Query("SELECT c FROM Categoria c LEFT JOIN FETCH c.productos")
    List<Categoria> findAllWithProductos();
    
    List<Categoria> findByNombreContainingIgnoreCase(String nombre);
}
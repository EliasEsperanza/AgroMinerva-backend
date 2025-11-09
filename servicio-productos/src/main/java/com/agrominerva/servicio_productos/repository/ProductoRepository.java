package com.agrominerva.servicio_productos.repository;

import com.agrominerva.servicio_productos.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, UUID> {
    
    List<Producto> findByCategoria_Id(UUID categoriaId);
    
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    
    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.categoria WHERE p.id = :id")
    Optional<Producto> findByIdWithCategoria(@Param("id") UUID id);
    
    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.categoria")
    List<Producto> findAllWithCategoria();
    
    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.categoria WHERE p.categoria.id = :categoriaId")
    List<Producto> findByCategoriaIdWithCategoria(@Param("categoriaId") UUID categoriaId);
    
    boolean existsByNombreAndCategoria_Id(String nombre, UUID categoriaId);
}
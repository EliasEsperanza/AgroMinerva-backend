package com.agrominerva.servicio_comercial.repository;

import com.agrominerva.servicio_comercial.entity.ProductoDestacado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface ProductoDestacadoRepository extends JpaRepository<ProductoDestacado, UUID> {
    
    List<ProductoDestacado> findAllByOrderByOrdenAsc();
    
    boolean existsByProductoId(UUID productoId);

    // ✅ AGREGAR ESTE MÉTODO FALTANTE
    Optional<ProductoDestacado> findByProductoId(UUID productoId);
    
    @Query("SELECT MAX(p.orden) FROM ProductoDestacado p")
    Integer findMaxOrden();
}
package com.agrominerva.servicio_inventario.repository;

import com.agrominerva.servicio_inventario.entity.InventarioStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventarioStockRepository extends JpaRepository<InventarioStock, UUID> {
    
    Optional<InventarioStock> findByProductoId(UUID productoId);
    
    boolean existsByProductoId(UUID productoId);
    
    @Modifying
    @Query("UPDATE InventarioStock i SET i.stock = i.stock - :cantidad WHERE i.productoId = :productoId AND i.stock >= :cantidad")
    int descontarStock(@Param("productoId") UUID productoId, @Param("cantidad") Integer cantidad);
    
    @Modifying
    @Query("UPDATE InventarioStock i SET i.stock = i.stock + :cantidad WHERE i.productoId = :productoId")
    int aumentarStock(@Param("productoId") UUID productoId, @Param("cantidad") Integer cantidad);
    
    void deleteByProductoId(UUID productoId);
}
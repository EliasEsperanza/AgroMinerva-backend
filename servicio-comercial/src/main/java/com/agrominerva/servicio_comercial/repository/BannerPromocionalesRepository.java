package com.agrominerva.servicio_comercial.repository;

import com.agrominerva.servicio_comercial.entity.BannerPromocionales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BannerPromocionalesRepository extends JpaRepository<BannerPromocionales, UUID> {
    
    List<BannerPromocionales> findByActivoTrueOrderByOrdenAsc();
    
    List<BannerPromocionales> findAllByOrderByOrdenAsc();
    
    @Query("SELECT MAX(b.orden) FROM BannerPromocionales b")
    Integer findMaxOrden();
}
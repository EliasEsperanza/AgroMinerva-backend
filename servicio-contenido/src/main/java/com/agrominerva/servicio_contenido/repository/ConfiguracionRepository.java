package com.agrominerva.servicio_contenido.repository;

import com.agrominerva.servicio_contenido.model.ConfiguracionSitio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfiguracionRepository extends JpaRepository<ConfiguracionSitio, Integer> {
    
    Optional<ConfiguracionSitio> findByClave(String clave);
    
    boolean existsByClave(String clave);
}
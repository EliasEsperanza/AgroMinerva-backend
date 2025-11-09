package com.agrominerva.servicio_contenido.service;

import com.agrominerva.servicio_contenido.dto.ConfiguracionRequest;
import com.agrominerva.servicio_contenido.dto.ConfiguracionResponse;
import com.agrominerva.servicio_contenido.model.ConfiguracionSitio;
import com.agrominerva.servicio_contenido.repository.ConfiguracionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ConfiguracionService {
    
    private final ConfiguracionRepository configuracionRepository;
    
    public ConfiguracionResponse crear(ConfiguracionRequest request) {
        log.info("Creando configuración: {}", request.getClave());
        
        if (configuracionRepository.existsByClave(request.getClave())) {
            throw new RuntimeException("Ya existe una configuración con la clave: " + request.getClave());
        }
        
        ConfiguracionSitio config = new ConfiguracionSitio();
        config.setClave(request.getClave().toUpperCase());
        config.setValor(request.getValor());
        config.setDescripcion(request.getDescripcion());
        
        ConfiguracionSitio saved = configuracionRepository.save(config);
        log.info("Configuración creada con ID: {}", saved.getId());
        
        return mapToResponse(saved);
    }
    
    public ConfiguracionResponse actualizar(Integer id, ConfiguracionRequest request) {
        log.info("Actualizando configuración con ID: {}", id);
        
        ConfiguracionSitio config = configuracionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuración no encontrada con ID: " + id));
        
        config.setValor(request.getValor());
        if (request.getDescripcion() != null) {
            config.setDescripcion(request.getDescripcion());
        }
        
        ConfiguracionSitio updated = configuracionRepository.save(config);
        log.info("Configuración actualizada: {}", updated.getId());
        
        return mapToResponse(updated);
    }
    
    public ConfiguracionResponse actualizarPorClave(String clave, String valor) {
        log.info("Actualizando configuración por clave: {}", clave);
        
        ConfiguracionSitio config = configuracionRepository.findByClave(clave.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Configuración no encontrada con clave: " + clave));
        
        config.setValor(valor);
        
        ConfiguracionSitio updated = configuracionRepository.save(config);
        log.info("Configuración actualizada: {}", updated.getClave());
        
        return mapToResponse(updated);
    }
    
    @Transactional(readOnly = true)
    public ConfiguracionResponse obtenerPorId(Integer id) {
        log.info("Obteniendo configuración por ID: {}", id);
        
        ConfiguracionSitio config = configuracionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuración no encontrada con ID: " + id));
        
        return mapToResponse(config);
    }
    
    @Transactional(readOnly = true)
    public ConfiguracionResponse obtenerPorClave(String clave) {
        log.info("Obteniendo configuración por clave: {}", clave);
        
        ConfiguracionSitio config = configuracionRepository.findByClave(clave.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Configuración no encontrada con clave: " + clave));
        
        return mapToResponse(config);
    }
    
    @Transactional(readOnly = true)
    public List<ConfiguracionResponse> listarTodas() {
        log.info("Listando todas las configuraciones");
        
        return configuracionRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    
    public void eliminar(Integer id) {
        log.info("Eliminando configuración con ID: {}", id);
        
        if (!configuracionRepository.existsById(id)) {
            throw new RuntimeException("Configuración no encontrada con ID: " + id);
        }
        
        configuracionRepository.deleteById(id);
        log.info("Configuración eliminada: {}", id);
    }
    
    private ConfiguracionResponse mapToResponse(ConfiguracionSitio config) {
        return ConfiguracionResponse.builder()
                .id(config.getId())
                .clave(config.getClave())
                .valor(config.getValor())
                .descripcion(config.getDescripcion())
                .creadoEn(config.getCreadoEn())
                .actualizadoEn(config.getActualizadoEn())
                .build();
    }
}
package com.agrominerva.servicio_comercial.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "banners_promocionales")
public class BannerPromocionales {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false)
    private String titulo;
    
    @Column(name = "imagen_url", nullable = false)
    private String imagenUrl;
    
    @Column(name = "enlace_url")
    private String enlaceUrl;
    
    @Column(nullable = false)
    private Boolean activo = true;
    
    @Column(nullable = false)
    private Integer orden = 0;
    
    // Constructores
    public BannerPromocionales() {}
    
    public BannerPromocionales(String titulo, String imagenUrl, String enlaceUrl, Boolean activo, Integer orden) {
        this.titulo = titulo;
        this.imagenUrl = imagenUrl;
        this.enlaceUrl = enlaceUrl;
        this.activo = activo;
        this.orden = orden;
    }
    
    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
    
    public String getEnlaceUrl() { return enlaceUrl; }
    public void setEnlaceUrl(String enlaceUrl) { this.enlaceUrl = enlaceUrl; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
}
package com.agrominerva.servicio_contenido.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "blog_posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogPost {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false, length = 500)
    private String titulo;
    
    @Column(nullable = false, unique = true, length = 500)
    private String slug;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String contenido;
    
    @Column(name = "imagen_portada_url", length = 1000)
    private String imagenPortadaUrl;
    
    @Column(name = "publicado_en")
    private LocalDateTime publicadoEn;
    
    @CreationTimestamp
    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;
    
    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;
    
    @Column(nullable = false)
    private Boolean publicado = false;
    
    @PrePersist
    public void prePersist() {
        if (publicado && publicadoEn == null) {
            publicadoEn = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    public void preUpdate() {
        if (publicado && publicadoEn == null) {
            publicadoEn = LocalDateTime.now();
        } else if (!publicado) {
            publicadoEn = null;
        }
    }
}
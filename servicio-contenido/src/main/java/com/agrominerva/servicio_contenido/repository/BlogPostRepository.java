package com.agrominerva.servicio_contenido.repository;

import com.agrominerva.servicio_contenido.model.BlogPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, UUID> {
    
    Optional<BlogPost> findBySlug(String slug);
    
    boolean existsBySlug(String slug);
    
    Page<BlogPost> findByPublicadoTrue(Pageable pageable);
    
    Page<BlogPost> findByPublicadoTrueOrderByPublicadoEnDesc(Pageable pageable);
}

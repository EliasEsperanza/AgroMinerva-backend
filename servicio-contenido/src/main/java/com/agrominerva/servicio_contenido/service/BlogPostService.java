package com.agrominerva.servicio_contenido.service;

import com.agrominerva.servicio_contenido.dto.BlogPostRequest;
import com.agrominerva.servicio_contenido.dto.BlogPostResponse;
import com.agrominerva.servicio_contenido.model.BlogPost;
import com.agrominerva.servicio_contenido.repository.BlogPostRepository;
import com.agrominerva.servicio_contenido.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BlogPostService {
    
    private final BlogPostRepository blogPostRepository;
    
    public BlogPostResponse crearPost(BlogPostRequest request) {
        log.info("Creando nuevo post: {}", request.getTitulo());
        
        BlogPost post = new BlogPost();
        post.setTitulo(request.getTitulo());
        post.setContenido(request.getContenido());
        post.setImagenPortadaUrl(request.getImagenPortadaUrl());
        post.setPublicado(request.getPublicado() != null ? request.getPublicado() : false);
        
        String slug = generarSlugUnico(request.getTitulo());
        post.setSlug(slug);
        
        BlogPost saved = blogPostRepository.save(post);
        log.info("Post creado con ID: {} y slug: {}", saved.getId(), saved.getSlug());
        
        return mapToResponse(saved);
    }
    
    public BlogPostResponse actualizarPost(UUID id, BlogPostRequest request) {
        log.info("Actualizando post con ID: {}", id);
        
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado con ID: " + id));
        
        post.setTitulo(request.getTitulo());
        post.setContenido(request.getContenido());
        post.setImagenPortadaUrl(request.getImagenPortadaUrl());
        
        if (request.getPublicado() != null) {
            post.setPublicado(request.getPublicado());
        }
        
        if (!post.getSlug().equals(SlugUtil.toSlug(request.getTitulo()))) {
            String nuevoSlug = generarSlugUnico(request.getTitulo());
            post.setSlug(nuevoSlug);
        }
        
        BlogPost updated = blogPostRepository.save(post);
        log.info("Post actualizado: {}", updated.getId());
        
        return mapToResponse(updated);
    }
    
    @Transactional(readOnly = true)
    public BlogPostResponse obtenerPorId(UUID id) {
        log.info("Obteniendo post por ID: {}", id);
        
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post no encontrado con ID: " + id));
        
        return mapToResponse(post);
    }
    
    @Transactional(readOnly = true)
    public BlogPostResponse obtenerPorSlug(String slug) {
        log.info("Obteniendo post por slug: {}", slug);
        
        BlogPost post = blogPostRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Post no encontrado con slug: " + slug));
        
        return mapToResponse(post);
    }
    
    @Transactional(readOnly = true)
    public Page<BlogPostResponse> listarTodos(Pageable pageable) {
        log.info("Listando todos los posts - página: {}", pageable.getPageNumber());
        
        return blogPostRepository.findAll(pageable)
                .map(this::mapToResponse);
    }
    
    @Transactional(readOnly = true)
    public Page<BlogPostResponse> listarPublicados(Pageable pageable) {
        log.info("Listando posts publicados - página: {}", pageable.getPageNumber());
        
        return blogPostRepository.findByPublicadoTrueOrderByPublicadoEnDesc(pageable)
                .map(this::mapToResponse);
    }
    
    public void eliminarPost(UUID id) {
        log.info("Eliminando post con ID: {}", id);
        
        if (!blogPostRepository.existsById(id)) {
            throw new RuntimeException("Post no encontrado con ID: " + id);
        }
        
        blogPostRepository.deleteById(id);
        log.info("Post eliminado: {}", id);
    }
    
    private String generarSlugUnico(String titulo) {
        String baseSlug = SlugUtil.toSlug(titulo);
        String slug = baseSlug;
        int attempt = 0;
        
        while (blogPostRepository.existsBySlug(slug)) {
            attempt++;
            slug = SlugUtil.generateUniqueSlug(baseSlug, attempt);
        }
        
        return slug;
    }
    
    private BlogPostResponse mapToResponse(BlogPost post) {
        return BlogPostResponse.builder()
                .id(post.getId())
                .titulo(post.getTitulo())
                .slug(post.getSlug())
                .contenido(post.getContenido())
                .imagenPortadaUrl(post.getImagenPortadaUrl())
                .publicado(post.getPublicado())
                .publicadoEn(post.getPublicadoEn())
                .creadoEn(post.getCreadoEn())
                .actualizadoEn(post.getActualizadoEn())
                .build();
    }
}
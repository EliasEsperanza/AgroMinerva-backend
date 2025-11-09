package com.agrominerva.servicio_contenido.controller;

import com.agrominerva.servicio_contenido.dto.BlogPostResponse;
import com.agrominerva.servicio_contenido.dto.ConfiguracionResponse;
import com.agrominerva.servicio_contenido.service.BlogPostService;
import com.agrominerva.servicio_contenido.service.ConfiguracionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
@Slf4j
public class PublicController {
    
    private final BlogPostService blogPostService;
    private final ConfiguracionService configuracionService;
    
    // =============== ENDPOINTS DE BLOG ===============
    
    @GetMapping("/blog")
    public ResponseEntity<Page<BlogPostResponse>> listarPostsPublicados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.info("GET /public/blog - página: {}, tamaño: {}", page, size);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("publicadoEn").descending());
        Page<BlogPostResponse> posts = blogPostService.listarPublicados(pageable);
        
        return ResponseEntity.ok(posts);
    }
    
    @GetMapping("/blog/{slug}")
    public ResponseEntity<BlogPostResponse> obtenerPostPorSlug(@PathVariable String slug) {
        log.info("GET /public/blog/{}", slug);
        
        BlogPostResponse post = blogPostService.obtenerPorSlug(slug);
        
        // Solo devolver si está publicado
        if (!post.getPublicado()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(post);
    }
    
    // =============== ENDPOINTS DE CONFIGURACIÓN ===============
    
    @GetMapping("/configuracion")
    public ResponseEntity<List<ConfiguracionResponse>> listarConfiguraciones() {
        log.info("GET /public/configuracion");
        
        List<ConfiguracionResponse> configs = configuracionService.listarTodas();
        return ResponseEntity.ok(configs);
    }
    
    @GetMapping("/configuracion/{clave}")
    public ResponseEntity<ConfiguracionResponse> obtenerConfiguracionPorClave(@PathVariable String clave) {
        log.info("GET /public/configuracion/{}", clave);
        
        try {
            ConfiguracionResponse config = configuracionService.obtenerPorClave(clave);
            return ResponseEntity.ok(config);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

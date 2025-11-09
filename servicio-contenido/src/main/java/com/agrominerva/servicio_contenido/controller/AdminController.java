package com.agrominerva.servicio_contenido.controller;

import com.agrominerva.servicio_contenido.dto.BlogPostRequest;
import com.agrominerva.servicio_contenido.dto.BlogPostResponse;
import com.agrominerva.servicio_contenido.dto.ConfiguracionRequest;
import com.agrominerva.servicio_contenido.dto.ConfiguracionResponse;
import com.agrominerva.servicio_contenido.service.BlogPostService;
import com.agrominerva.servicio_contenido.service.ConfiguracionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    
    private final BlogPostService blogPostService;
    private final ConfiguracionService configuracionService;
    
    // =============== ENDPOINTS DE BLOG (ADMIN) ===============
    
    @PostMapping("/blog")
    public ResponseEntity<BlogPostResponse> crearPost(@Valid @RequestBody BlogPostRequest request) {
        log.info("POST /admin/blog - Crear post: {}", request.getTitulo());
        
        BlogPostResponse response = blogPostService.crearPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PutMapping("/blog/{id}")
    public ResponseEntity<BlogPostResponse> actualizarPost(
            @PathVariable UUID id,
            @Valid @RequestBody BlogPostRequest request) {
        
        log.info("PUT /admin/blog/{} - Actualizar post", id);
        
        try {
            BlogPostResponse response = blogPostService.actualizarPost(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/blog")
    public ResponseEntity<Page<BlogPostResponse>> listarTodosLosPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.info("GET /admin/blog - página: {}, tamaño: {}", page, size);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("creadoEn").descending());
        Page<BlogPostResponse> posts = blogPostService.listarTodos(pageable);
        
        return ResponseEntity.ok(posts);
    }
    
    @GetMapping("/blog/{id}")
    public ResponseEntity<BlogPostResponse> obtenerPostPorId(@PathVariable UUID id) {
        log.info("GET /admin/blog/{}", id);
        
        try {
            BlogPostResponse post = blogPostService.obtenerPorId(id);
            return ResponseEntity.ok(post);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/blog/{id}")
    public ResponseEntity<Void> eliminarPost(@PathVariable UUID id) {
        log.info("DELETE /admin/blog/{}", id);
        
        try {
            blogPostService.eliminarPost(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // =============== ENDPOINTS DE CONFIGURACIÓN (ADMIN) ===============
    
    @PostMapping("/configuracion")
    public ResponseEntity<ConfiguracionResponse> crearConfiguracion(
            @Valid @RequestBody ConfiguracionRequest request) {
        
        log.info("POST /admin/configuracion - Crear: {}", request.getClave());
        
        try {
            ConfiguracionResponse response = configuracionService.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/configuracion/{id}")
    public ResponseEntity<ConfiguracionResponse> actualizarConfiguracion(
            @PathVariable Integer id,
            @Valid @RequestBody ConfiguracionRequest request) {
        
        log.info("PUT /admin/configuracion/{}", id);
        
        try {
            ConfiguracionResponse response = configuracionService.actualizar(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PatchMapping("/configuracion/{clave}")
    public ResponseEntity<ConfiguracionResponse> actualizarValorConfiguracion(
            @PathVariable String clave,
            @RequestBody String valor) {
        
        log.info("PATCH /admin/configuracion/{}", clave);
        
        try {
            ConfiguracionResponse response = configuracionService.actualizarPorClave(clave, valor);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/configuracion")
    public ResponseEntity<List<ConfiguracionResponse>> listarConfiguraciones() {
        log.info("GET /admin/configuracion");
        
        List<ConfiguracionResponse> configs = configuracionService.listarTodas();
        return ResponseEntity.ok(configs);
    }
    
    @GetMapping("/configuracion/{id}")
    public ResponseEntity<ConfiguracionResponse> obtenerConfiguracionPorId(@PathVariable Integer id) {
        log.info("GET /admin/configuracion/{}", id);
        
        try {
            ConfiguracionResponse config = configuracionService.obtenerPorId(id);
            return ResponseEntity.ok(config);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/configuracion/{id}")
    public ResponseEntity<Void> eliminarConfiguracion(@PathVariable Integer id) {
        log.info("DELETE /admin/configuracion/{}", id);
        
        try {
            configuracionService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

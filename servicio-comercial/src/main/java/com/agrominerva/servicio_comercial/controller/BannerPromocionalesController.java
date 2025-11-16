package com.agrominerva.servicio_comercial.controller;

import com.agrominerva.servicio_comercial.entity.BannerPromocionales;
import com.agrominerva.servicio_comercial.service.BannerPromocionalesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/banners")
@CrossOrigin(origins = "*")
public class BannerPromocionalesController {
    
    @Autowired
    private BannerPromocionalesService bannerService;
    
    @GetMapping
    public List<BannerPromocionales> getAllBanners() {
        return bannerService.findAll();
    }
    
    @GetMapping("/activos")
    public List<BannerPromocionales> getActiveBanners() {
        return bannerService.findActiveBanners();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BannerPromocionales> getBannerById(@PathVariable UUID id) {
        return bannerService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public BannerPromocionales createBanner(@Valid @RequestBody BannerPromocionales banner) {
        return bannerService.save(banner);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BannerPromocionales> updateBanner(
            @PathVariable UUID id, 
            @Valid @RequestBody BannerPromocionales bannerDetails) {
        try {
            BannerPromocionales updatedBanner = bannerService.update(id, bannerDetails);
            return ResponseEntity.ok(updatedBanner);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable UUID id) {
        bannerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
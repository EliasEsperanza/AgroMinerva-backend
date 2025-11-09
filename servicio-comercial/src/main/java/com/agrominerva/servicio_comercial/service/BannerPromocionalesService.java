package com.agrominerva.servicio_comercial.service;

import com.agrominerva.servicio_comercial.entity.BannerPromocionales;
import com.agrominerva.servicio_comercial.repository.BannerPromocionalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BannerPromocionalesService {
    
    @Autowired
    private BannerPromocionalesRepository bannerRepository;
    
    public List<BannerPromocionales> findAll() {
        return bannerRepository.findAllByOrderByOrdenAsc();
    }
    
    public List<BannerPromocionales> findActiveBanners() {
        return bannerRepository.findByActivoTrueOrderByOrdenAsc();
    }
    
    public Optional<BannerPromocionales> findById(UUID id) {
        return bannerRepository.findById(id);
    }
    
    public BannerPromocionales save(BannerPromocionales banner) {
        if (banner.getOrden() == null) {
            Integer maxOrden = bannerRepository.findMaxOrden();
            banner.setOrden(maxOrden != null ? maxOrden + 1 : 0);
        }
        return bannerRepository.save(banner);
    }
    
    public void deleteById(UUID id) {
        bannerRepository.deleteById(id);
    }
    
    public BannerPromocionales update(UUID id, BannerPromocionales bannerDetails) {
        return bannerRepository.findById(id)
            .map(banner -> {
                banner.setTitulo(bannerDetails.getTitulo());
                banner.setImagenUrl(bannerDetails.getImagenUrl());
                banner.setEnlaceUrl(bannerDetails.getEnlaceUrl());
                banner.setActivo(bannerDetails.getActivo());
                banner.setOrden(bannerDetails.getOrden());
                return bannerRepository.save(banner);
            })
            .orElseThrow(() -> new RuntimeException("Banner no encontrado con id: " + id));
    }
}
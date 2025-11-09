package com.agrominerva.servicio_ventas.controller;

import com.agrominerva.servicio_ventas.model.Pedido;
import com.agrominerva.servicio_ventas.service.PedidoService;
import com.agrominerva.servicio_ventas.dto.CrearPedidoRequest;
import com.agrominerva.servicio_ventas.dto.PedidoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> crear(@Valid @RequestBody CrearPedidoRequest req) {
        Pedido p = service.crearPedido(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(PedidoResponse.fromEntity(p));
    }
}

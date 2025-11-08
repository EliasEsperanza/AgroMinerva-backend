package com.agrominerva.servicio_ventas.kafka;

import com.agrominerva.servicio_ventas.dto.VentaDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class ProductorKafka {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public ProductorKafka(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public CompletableFuture<SendResult<String,Object>> enviarVentaRegistrada(VentaDTO payload) {
        return kafkaTemplate.send("topic-venta-registrada", payload);
    }
}


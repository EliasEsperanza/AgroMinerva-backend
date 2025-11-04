package com.agrominerva.servicio_usuarios.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data // Lombok: Genera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok: Genera un constructor sin argumentos (requerido por JPA)
@Entity // Le dice a JPA que esta clase es una tabla
@Table(name = "usuarios") // Mapea al nombre de tu tabla
public class Usuario {

    @Id // Marca como Primary Key
    @GeneratedValue(strategy = GenerationType.UUID) // Genera el UUID automáticamente
    private UUID id;

    @Column(nullable = false, unique = true) // Columna no nula y única
    private String email;

    @Column(name = "password_hash", nullable = false) // Mapea a 'password_hash'
    private String passwordHash;

    @Enumerated(EnumType.STRING) // Guarda el ENUM como String ("ADMIN") y no como un número (0)
    @Column(nullable = false)
    private Rol rol;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private boolean activo = true; // Valor por defecto

    @CreationTimestamp // Asigna la fecha y hora actual al crear
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
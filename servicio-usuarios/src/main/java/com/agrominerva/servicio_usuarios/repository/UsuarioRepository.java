package com.agrominerva.servicio_usuarios.repository;

import com.agrominerva.servicio_usuarios.entity.Usuario;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    // Spring Data JPA crea la consulta automáticamente por el nombre del método
    // "EncuentraPorEmail"
    Optional<Usuario> findByEmail(String email);
}


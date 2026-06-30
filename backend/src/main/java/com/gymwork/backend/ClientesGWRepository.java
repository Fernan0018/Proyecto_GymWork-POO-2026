package com.gymwork.backend;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientesGWRepository extends JpaRepository<ClientesGW, String> {
    List<ClientesGW> findByNombreContainingIgnoreCaseOrCodigoContainingIgnoreCase(
        String nombre,
        String codigo
    );
}

package com.gymwork.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientesGWRepository extends JpaRepository<ClientesGW, Long> {
    // Hereda de forma automática todas las funciones de guardado para ClientesGW
}

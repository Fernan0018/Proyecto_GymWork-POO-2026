package com.gymwork.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;



public interface ClientesGWRepository extends JpaRepository<ClientesGW, String> {
    List<ClientesGW> findByNombresClienteContainingIgnoreCaseOrCodigoClienteContainingIgnoreCase(
        String nombre,
        String codigo
);

}

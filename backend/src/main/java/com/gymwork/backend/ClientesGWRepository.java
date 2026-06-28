package com.gymwork.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClientesGWRepository extends JpaRepository<ClientesGW, String> {
    List<ClientesGW> findByNombreContainingIgnoreCaseOrCodigoContainingIgnoreCase(
        String nombre,
        String codigo
    );
}

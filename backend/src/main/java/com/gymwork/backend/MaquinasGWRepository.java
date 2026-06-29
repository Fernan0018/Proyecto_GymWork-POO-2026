package com.gymwork.backend;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaquinasGWRepository extends JpaRepository<MaquinasGW, Integer> {
    long countByEstadoMaquina(String estadoMaquina);
    List<MaquinasGW> findByNombreModeloContainingIgnoreCase(String nombreModelo);
}

package com.gymwork.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SedesGWRepository extends JpaRepository<SedesGW, Integer> {
    List<SedesGW> findByNombreSedeContainingIgnoreCaseOrDepartamentoContainingIgnoreCase(
        String nombre,
        String departamento
    );
}

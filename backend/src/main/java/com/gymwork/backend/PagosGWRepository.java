package com.gymwork.backend; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagosGWRepository extends JpaRepository<PagosGW, Long> {
    // Hereda de forma automática todas las funciones de guardado para PagosGW
}
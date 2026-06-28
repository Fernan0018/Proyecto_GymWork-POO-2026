package com.gymwork.backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TiemposTicketGWRepository extends JpaRepository<TiemposTicketGW, Integer> {
    java.util.List<TiemposTicketGW> findByDiaResolucion(java.time.LocalDate diaResolucion);
}

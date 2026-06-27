package com.gymwork.backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SoporteGWRepository extends JpaRepository<SoporteGW, Integer> {
    java.util.List<SoporteGW> findByEstadoTicket(String estadoTicket);
}

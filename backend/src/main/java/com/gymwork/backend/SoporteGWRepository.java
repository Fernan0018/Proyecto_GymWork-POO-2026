package com.gymwork.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SoporteGWRepository extends JpaRepository<SoporteGW, Integer>, JpaSpecificationExecutor<SoporteGW> {
    java.util.List<SoporteGW> findByEstadoTicket(String estadoTicket);
}

package com.gymwork.backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface RegistroTiempoPromGWRepository extends JpaRepository<RegistroTiempoPromGW, LocalDate> {
}

package com.gymwork.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DatosAdminRepository extends JpaRepository<DatosAdmin, Integer> {

    Optional<DatosAdmin> findByUsuarioUsername(String username);

}

package com.gymwork.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DatosAdminGWRepository extends JpaRepository<DatosAdminGW, String> {

    Optional<DatosAdminGW> findByUsuarioUsername(String username);

}

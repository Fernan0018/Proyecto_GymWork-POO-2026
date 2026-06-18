package com.gymwork.backend;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosGWRepository extends JpaRepository<UsuariosGW, Integer> {

    UsuariosGW findByUsername(String username);

}

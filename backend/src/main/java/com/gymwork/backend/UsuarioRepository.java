package com.gymwork.backend;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    
    // Spring buscará automáticamente por la columna usuario
    Usuario findByUsername(String username);
    
}
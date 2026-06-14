package com.gymwork.backend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LoginController {

    private final UsuarioRepository usuarioRepository;

    public LoginController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> verificarLogin(@RequestBody Usuario usuarioWeb) {
        
        // 1. Buscamos al usuario en la base de datos
        Usuario usuarioDB = usuarioRepository.findByUsername(usuarioWeb.getUsername());

        // 2. Si no existe, rechazamos
        if (usuarioDB == null) {
            return ResponseEntity.status(401).body("El usuario no existe");
        }

        // 3. Si existe, comparamos contraseñas de forma segura (sin imprimir nada)
        if (usuarioWeb.getPassword().equals(usuarioDB.getPassword())) {
            return ResponseEntity.ok("Acceso concedido");
        } else {
            return ResponseEntity.status(401).body("Contraseña incorrecta");
        }
    }
}
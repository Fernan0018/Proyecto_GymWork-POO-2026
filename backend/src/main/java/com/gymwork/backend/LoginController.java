package com.gymwork.backend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LoginController {

    private final UsuarioRepository usuarioRepository;
    private final DatosAdminRepository datosAdminRepository;

    public LoginController(UsuarioRepository usuarioRepository, DatosAdminRepository datosAdminRepository) {
        this.usuarioRepository = usuarioRepository;
        this.datosAdminRepository = datosAdminRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> verificarLogin(@RequestBody Usuario usuarioWeb) {
        
        Usuario usuarioDB = usuarioRepository.findByUsername(usuarioWeb.getUsername());

        if (usuarioDB == null) {
            return ResponseEntity.status(401).body("El usuario no existe");
        }

        if (!usuarioWeb.getUsername().equals(usuarioDB.getUsername())) {
            return ResponseEntity.status(401).body("El usuario no existe");
        }

        if (usuarioWeb.getPassword().equals(usuarioDB.getPassword())) {
            return ResponseEntity.ok("Acceso concedido");
        } else {
            return ResponseEntity.status(401).body("Contrasena incorrecta");
        }
    }

    @GetMapping("/perfil")
    public ResponseEntity<?> verPerfil(@RequestParam String username) {
        Usuario usuarioDB = usuarioRepository.findByUsername(username);

        if (usuarioDB == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        var datosOpt = datosAdminRepository.findByUsuarioUsername(username);

        if (datosOpt.isEmpty()) {
            return ResponseEntity.status(404).body("No se encontraron datos del perfil");
        }

        DatosAdmin datos = datosOpt.get();

        record PerfilResponse(
            String nombre,
            String apellidos,
            String fechaNacimiento,
            Integer edadActual,
            String correo,
            String telefono
        ) {}

        PerfilResponse response = new PerfilResponse(
            datos.getNombre(),
            datos.getApellidos(),
            datos.getFechaNacimiento().toString(),
            datos.getEdadActual(),
            datos.getCorreo(),
            datos.getTelefono()
        );

        return ResponseEntity.ok(response);
    }
}

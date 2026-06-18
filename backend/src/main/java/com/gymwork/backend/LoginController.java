package com.gymwork.backend;

import java.time.LocalDate;
import java.time.Period;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LoginController {

    private final UsuariosGWRepository usuariosGWRepository;
    private final DatosAdminGWRepository datosAdminGWRepository;

    public LoginController(UsuariosGWRepository usuariosGWRepository, DatosAdminGWRepository datosAdminGWRepository) {
        this.usuariosGWRepository = usuariosGWRepository;
        this.datosAdminGWRepository = datosAdminGWRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> verificarLogin(@RequestBody UsuariosGW usuarioWeb) {

        UsuariosGW usuarioDB = usuariosGWRepository.findByUsername(usuarioWeb.getUsername());

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
        UsuariosGW usuarioDB = usuariosGWRepository.findByUsername(username);

        if (usuarioDB == null) {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        var datosOpt = datosAdminGWRepository.findByUsuarioUsername(username);

        if (datosOpt.isEmpty()) {
            return ResponseEntity.status(404).body("No se encontraron datos del perfil");
        }

        DatosAdminGW datos = datosOpt.get();

        int edad = Period.between(datos.getFechaNacimiento(), LocalDate.now()).getYears();

        record PerfilResponse(
            String dni,
            String nombre,
            String apellidos,
            String fechaNacimiento,
            int edadActual,
            String correo,
            String telefono
        ) {}

        PerfilResponse response = new PerfilResponse(
            datos.getDni(),
            datos.getNombre(),
            datos.getApellidos(),
            datos.getFechaNacimiento().toString(),
            edad,
            datos.getCorreo(),
            datos.getTelefono()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/perfil")
    public ResponseEntity<String> actualizarPerfil(@RequestBody PerfilUpdateRequest request) {
        var datosOpt = datosAdminGWRepository.findById(request.dni());
        if (datosOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Perfil no encontrado");
        }

        DatosAdminGW datos = datosOpt.get();
        datos.setNombre(request.nombre());
        datos.setApellidos(request.apellidos());
        datos.setFechaNacimiento(LocalDate.parse(request.fechaNacimiento()));
        datos.setCorreo(request.correo());
        datos.setTelefono(request.telefono());

        datosAdminGWRepository.save(datos);

        return ResponseEntity.ok("Perfil editado correctamente");
    }

    public record PerfilUpdateRequest(
        String dni,
        String nombre,
        String apellidos,
        String fechaNacimiento,
        String correo,
        String telefono
    ) {}
}

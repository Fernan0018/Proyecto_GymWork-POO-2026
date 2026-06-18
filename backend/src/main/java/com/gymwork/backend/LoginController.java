package com.gymwork.backend;

import java.time.LocalDate;
import java.time.Period;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UsuariosGWRepository usuariosGWRepository;
    private final DatosAdminGWRepository datosAdminGWRepository;

    public LoginController(UsuariosGWRepository usuariosGWRepository, DatosAdminGWRepository datosAdminGWRepository) {
        this.usuariosGWRepository = usuariosGWRepository;
        this.datosAdminGWRepository = datosAdminGWRepository;
    }

    @GetMapping({"/", "/login"})
    public String login(HttpSession session) {
        if (session.getAttribute("username") != null) {
            return "redirect:/menu";
        }
        return "login";
    }

    @PostMapping("/login")
    public String verificarLogin(@RequestParam String username, @RequestParam String password,
                                  HttpSession session, RedirectAttributes redirectAttrs) {
        UsuariosGW usuarioDB = usuariosGWRepository.findByUsername(username);

        if (usuarioDB == null || !usuarioDB.getPassword().equals(password)) {
            redirectAttrs.addFlashAttribute("error", "Acceso inválido. Por favor, inténtelo otra vez.");
            return "redirect:/login";
        }

        session.setAttribute("username", username);
        return "redirect:/menu";
    }

    @GetMapping("/menu")
    public String menu(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        var datosOpt = datosAdminGWRepository.findByUsuarioUsername(username);
        if (datosOpt.isPresent()) {
            DatosAdminGW datos = datosOpt.get();
            model.addAttribute("perfil", datos);
            int edad = Period.between(datos.getFechaNacimiento(), LocalDate.now()).getYears();
            model.addAttribute("edad", edad);
        }

        model.addAttribute("username", username);
        return "menu";
    }

    @PostMapping("/perfil/actualizar")
    public String actualizarPerfil(@RequestParam String dni,
                                    @RequestParam String nombre,
                                    @RequestParam String apellidos,
                                    @RequestParam String fechaNacimiento,
                                    @RequestParam String correo,
                                    @RequestParam String telefono,
                                    RedirectAttributes redirectAttrs) {
        var datosOpt = datosAdminGWRepository.findById(dni);
        if (datosOpt.isPresent()) {
            DatosAdminGW datos = datosOpt.get();
            datos.setNombre(nombre);
            datos.setApellidos(apellidos);
            datos.setFechaNacimiento(LocalDate.parse(fechaNacimiento));
            datos.setCorreo(correo);
            datos.setTelefono(telefono);
            datosAdminGWRepository.save(datos);
            redirectAttrs.addFlashAttribute("exito", "PERFIL EDITADO CORRECTAMENTE");
        } else {
            redirectAttrs.addFlashAttribute("error", "Perfil no encontrado");
        }
        return "redirect:/menu";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

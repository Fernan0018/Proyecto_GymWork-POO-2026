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
                                  HttpSession session, Model model) {
        UsuariosGW usuarioDB = usuariosGWRepository.findByUsername(username);

        if (usuarioDB == null || !usuarioDB.getPassword().equals(password)) {
            model.addAttribute("error", "Acceso inválido. Por favor, inténtelo otra vez.");
            model.addAttribute("username", username);
            return "login";
        }

        session.setAttribute("username", username);
        var datosOpt = datosAdminGWRepository.findByUsuarioUsername(username);
        datosOpt.ifPresent(datos -> session.setAttribute("perfil", datos));
        return "redirect:/menu";
    }

    @GetMapping("/menu")
    public String menu(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        DatosAdminGW perfil = (DatosAdminGW) session.getAttribute("perfil");
        if (perfil == null) {
            var datosOpt = datosAdminGWRepository.findByUsuarioUsername(username);
            if (datosOpt.isPresent()) {
                perfil = datosOpt.get();
                session.setAttribute("perfil", perfil);
            }
        }
        if (perfil != null) {
            model.addAttribute("perfil", perfil);
            int edad = Period.between(perfil.getFechaNacimiento(), LocalDate.now()).getYears();
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
        if (nombre == null || nombre.trim().length() < 2) {
            redirectAttrs.addFlashAttribute("error", "El nombre debe tener al menos 2 caracteres");
            return "redirect:/menu";
        }
        if (apellidos == null || apellidos.trim().length() < 2) {
            redirectAttrs.addFlashAttribute("error", "Los apellidos deben tener al menos 2 caracteres");
            return "redirect:/menu";
        }
        if (correo == null || !correo.matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            redirectAttrs.addFlashAttribute("error", "Correo electrónico no válido");
            return "redirect:/menu";
        }
        if (telefono == null || telefono.replaceAll("\\D", "").length() < 9) {
            redirectAttrs.addFlashAttribute("error", "Teléfono debe tener al menos 9 dígitos");
            return "redirect:/menu";
        }
        if (fechaNacimiento == null || fechaNacimiento.isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "La fecha de nacimiento es obligatoria");
            return "redirect:/menu";
        }
        try {
            LocalDate.parse(fechaNacimiento);
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Fecha de nacimiento no válida");
            return "redirect:/menu";
        }
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

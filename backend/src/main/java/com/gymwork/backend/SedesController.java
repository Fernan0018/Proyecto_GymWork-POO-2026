package com.gymwork.backend;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Controller
public class SedesController {

    private final SedesGWRepository sedesRepository;
    private final UbiGeoRepository ubiGeoRepository;
    private final UsuariosGWRepository usuariosRepository;
    private final DatosAdminGWRepository datosAdminRepository;

    public SedesController(SedesGWRepository sedesRepository, UbiGeoRepository ubiGeoRepository, UsuariosGWRepository usuariosRepository, DatosAdminGWRepository datosAdminRepository) {
        this.sedesRepository = sedesRepository;
        this.ubiGeoRepository = ubiGeoRepository;
        this.usuariosRepository = usuariosRepository;
        this.datosAdminRepository = datosAdminRepository;
    }

    @GetMapping("/sedes")
    public String sedes(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }
        DatosAdminGW perfil = (DatosAdminGW) session.getAttribute("perfil");
        if (perfil == null) {
            var datosOpt = datosAdminRepository.findByUsuarioUsername(username);
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
        model.addAttribute("sedes", sedesRepository.findAll());
        model.addAttribute("ubigeos", ubiGeoRepository.findAll());
        return "sedes";
    }

    @GetMapping("/sedes/mapa")
    public String mapa(Model model) {
        model.addAttribute("sedes", sedesRepository.findAll());
        return "mapa";
    }

    @PostMapping("/sedes/guardar")
    public String guardarSede(
            @RequestParam String nombre,
            @RequestParam String ubigeo,
            @RequestParam String estado,
            @RequestParam Integer aforo,
            RedirectAttributes redirect) {

        if (ubigeo == null || ubigeo.length() < 4 || !ubigeo.matches("\\d+")) {
            redirect.addFlashAttribute("error", "El c\u00F3digo UBIGEO debe tener al menos 4 d\u00EDgitos");
            return "redirect:/sedes";
        }

        String codDepartamento = ubigeo.substring(0, 2);
        String codDistrito = ubigeo.substring(0, 4);

        List<UbiGeo> depList = ubiGeoRepository.findByCodigoStartingWith(codDepartamento);
        List<UbiGeo> disList = ubiGeoRepository.findByCodigoStartingWith(codDistrito);

        if (depList.isEmpty() || disList.isEmpty()) {
            redirect.addFlashAttribute("error", "El c\u00F3digo UBIGEO no es v\u00E1lido");
            return "redirect:/sedes";
        }

        SedesGW sede = new SedesGW();
        sede.setNombreSede(nombre);
        sede.setDepartamento(depList.get(0).getNombre());
        sede.setDistrito(disList.get(0).getNombre());
        sede.setEstado(estado);
        sede.setAforo(aforo);

        sedesRepository.save(sede);
        redirect.addFlashAttribute("exito", "Sede registrada correctamente");
        return "redirect:/sedes";
    }

    @PostMapping("/sedes/actualizar")
    public String actualizarSede(
            @RequestParam Integer id,
            @RequestParam String estado,
            @RequestParam Integer aforo,
            RedirectAttributes redirect) {

        Optional<SedesGW> opt = sedesRepository.findById(id);
        if (opt.isEmpty()) {
            redirect.addFlashAttribute("error", "La sede no existe");
            return "redirect:/sedes";
        }

        SedesGW sede = opt.get();
        sede.setEstado(estado);
        sede.setAforo(aforo);

        sedesRepository.save(sede);
        redirect.addFlashAttribute("exito", "Sede actualizada correctamente");
        return "redirect:/sedes";
    }

    @PostMapping("/sedes/eliminar")
    public String eliminarSede(
            @RequestParam Integer id,
            @RequestParam String deletePassword,
            HttpSession session,
            RedirectAttributes redirect) {

        String username = (String) session.getAttribute("username");
        if (username == null) {
            redirect.addFlashAttribute("error", "Debe iniciar sesión");
            return "redirect:/login";
        }

        UsuariosGW usuario = usuariosRepository.findByUsername(username);
        if (usuario == null) {
            redirect.addFlashAttribute("error", "Usuario no encontrado");
            return "redirect:/sedes";
        }

        if (!usuario.getDeletePassword().equals(deletePassword)) {
            redirect.addFlashAttribute("error", "Contraseña de eliminación incorrecta");
            return "redirect:/sedes";
        }

        Optional<SedesGW> opt = sedesRepository.findById(id);
        if (opt.isEmpty()) {
            redirect.addFlashAttribute("error", "La sede no existe");
            return "redirect:/sedes";
        }

        sedesRepository.delete(opt.get());
        redirect.addFlashAttribute("exito", "Sede eliminada correctamente");
        return "redirect:/sedes";
    }
}

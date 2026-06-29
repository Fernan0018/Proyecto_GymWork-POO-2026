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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MaquinasController {

    private final MaquinasGWRepository maquinasRepository;
    private final SedesGWRepository sedesRepository;
    private final DatosAdminGWRepository datosAdminRepository;
    private final UsuariosGWRepository usuariosRepository;

    public MaquinasController(MaquinasGWRepository maquinasRepository, SedesGWRepository sedesRepository, DatosAdminGWRepository datosAdminRepository, UsuariosGWRepository usuariosRepository) {
        this.maquinasRepository = maquinasRepository;
        this.sedesRepository = sedesRepository;
        this.datosAdminRepository = datosAdminRepository;
        this.usuariosRepository = usuariosRepository;
    }

    @GetMapping("/maquinas")
    public String maquinas(HttpSession session, Model model) {
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

        List<MaquinasGW> maquinas = maquinasRepository.findAll();
        model.addAttribute("maquinas", maquinas);

        long total = maquinas.size();
        long operativas = maquinasRepository.countByEstadoMaquina("Operativa");
        long mantenimiento = maquinasRepository.countByEstadoMaquina("Mantenimiento");
        long inactivas = maquinasRepository.countByEstadoMaquina("Inactiva");

        model.addAttribute("totalMaquinas", total);
        model.addAttribute("totalOperativas", operativas);
        model.addAttribute("totalMantenimiento", mantenimiento);
        model.addAttribute("totalInactivas", inactivas);

        Map<Integer, String> sedeMap = new HashMap<>();
        for (SedesGW sede : sedesRepository.findAll()) {
            sedeMap.put(sede.getIdSede(), sede.getNombreSede());
        }
        model.addAttribute("sedeMap", sedeMap);
        model.addAttribute("sedes", sedesRepository.findAll());

        return "maquinas";
    }

    @PostMapping("/maquinas/guardar")
    public String guardarMaquina(
            @RequestParam("nombreModelo") String nombreModelo,
            @RequestParam("idSede") Integer idSede,
            @RequestParam("estadoMaquina") String estadoMaquina,
            @RequestParam(value = "ultimoMantenimiento", required = false) String ultimoMantenimiento,
            HttpSession session,
            RedirectAttributes redirect) {

        String username = (String) session.getAttribute("username");
        if (username == null) {
            redirect.addFlashAttribute("error", "Debe iniciar sesi\u00F3n");
            return "redirect:/login";
        }

        if (nombreModelo == null || nombreModelo.trim().isEmpty()) {
            redirect.addFlashAttribute("error", "El nombre/modelo es obligatorio");
            return "redirect:/maquinas";
        }

        if (!isValidEstado(estadoMaquina)) {
            redirect.addFlashAttribute("error", "El estado seleccionado no es v\u00E1lido");
            return "redirect:/maquinas";
        }

        MaquinasGW maquina = new MaquinasGW();
        maquina.setNombreModelo(nombreModelo.trim());
        maquina.setIdSede(idSede != null && idSede > 0 ? idSede : null);
        maquina.setEstadoMaquina(estadoMaquina);
        if (ultimoMantenimiento != null && !ultimoMantenimiento.isEmpty()) {
            maquina.setUltimoMantenimiento(LocalDate.parse(ultimoMantenimiento));
        }
        maquinasRepository.save(maquina);
        redirect.addFlashAttribute("exito", "M\u00E1quina agregada correctamente");
        return "redirect:/maquinas";
    }

    @PostMapping("/maquinas/actualizar")
    public String actualizarMaquina(
            @RequestParam("idMaquina") Integer idMaquina,
            @RequestParam("nombreModelo") String nombreModelo,
            @RequestParam("idSede") Integer idSede,
            @RequestParam("estadoMaquina") String estadoMaquina,
            @RequestParam(value = "ultimoMantenimiento", required = false) String ultimoMantenimiento,
            HttpSession session,
            RedirectAttributes redirect) {

        String username = (String) session.getAttribute("username");
        if (username == null) {
            redirect.addFlashAttribute("error", "Debe iniciar sesi\u00F3n");
            return "redirect:/login";
        }

        if (nombreModelo == null || nombreModelo.trim().isEmpty()) {
            redirect.addFlashAttribute("error", "El nombre/modelo es obligatorio");
            return "redirect:/maquinas";
        }

        if (!isValidEstado(estadoMaquina)) {
            redirect.addFlashAttribute("error", "El estado seleccionado no es v\u00E1lido");
            return "redirect:/maquinas";
        }

        Optional<MaquinasGW> opt = maquinasRepository.findById(idMaquina);
        if (opt.isEmpty()) {
            redirect.addFlashAttribute("error", "La m\u00E1quina no existe");
            return "redirect:/maquinas";
        }

        MaquinasGW maquina = opt.get();
        maquina.setNombreModelo(nombreModelo.trim());
        maquina.setIdSede(idSede != null && idSede > 0 ? idSede : null);
        maquina.setEstadoMaquina(estadoMaquina);
        if (ultimoMantenimiento != null && !ultimoMantenimiento.isEmpty()) {
            maquina.setUltimoMantenimiento(LocalDate.parse(ultimoMantenimiento));
        } else {
            maquina.setUltimoMantenimiento(null);
        }
        maquinasRepository.save(maquina);
        redirect.addFlashAttribute("exito", "M\u00E1quina actualizada correctamente");
        return "redirect:/maquinas";
    }

    @PostMapping("/maquinas/eliminar")
    public String eliminarMaquina(
            @RequestParam("idMaquina") Integer idMaquina,
            @RequestParam("deletePassword") String deletePassword,
            HttpSession session,
            RedirectAttributes redirect) {

        String username = (String) session.getAttribute("username");
        if (username == null) {
            redirect.addFlashAttribute("error", "Debe iniciar sesi\u00F3n");
            return "redirect:/login";
        }

        Optional<UsuariosGW> userOpt = usuariosRepository.findByUsername(username);
        if (userOpt.isEmpty() || !userOpt.get().getContraseña().equals(deletePassword)) {
            redirect.addFlashAttribute("error", "Contrase\u00F1a de eliminaci\u00F3n incorrecta");
            return "redirect:/maquinas";
        }

        if (!maquinasRepository.existsById(idMaquina)) {
            redirect.addFlashAttribute("error", "La m\u00E1quina no existe");
            return "redirect:/maquinas";
        }

        maquinasRepository.deleteById(idMaquina);
        redirect.addFlashAttribute("exito", "M\u00E1quina eliminada correctamente");
        return "redirect:/maquinas";
    }

    private boolean isValidEstado(String estado) {
        return estado != null && (estado.equals("Operativa") || estado.equals("Mantenimiento") || estado.equals("Inactiva"));
    }

}

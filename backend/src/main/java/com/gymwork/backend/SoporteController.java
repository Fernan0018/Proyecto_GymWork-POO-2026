package com.gymwork.backend;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SoporteController {

    private final SoporteGWRepository soporteRepository;
    private final SedesGWRepository sedesRepository;
    private final MaquinasGWRepository maquinasRepository;
    private final DatosAdminGWRepository datosAdminRepository;

    public SoporteController(SoporteGWRepository soporteRepository, SedesGWRepository sedesRepository, MaquinasGWRepository maquinasRepository, DatosAdminGWRepository datosAdminRepository) {
        this.soporteRepository = soporteRepository;
        this.sedesRepository = sedesRepository;
        this.maquinasRepository = maquinasRepository;
        this.datosAdminRepository = datosAdminRepository;
    }

    @GetMapping("/soporte")
    public String soporte(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            var datosOpt = datosAdminRepository.findByUsuarioUsername(username);
            if (datosOpt.isPresent()) {
                DatosAdminGW datos = datosOpt.get();
                model.addAttribute("perfil", datos);
                int edad = Period.between(datos.getFechaNacimiento(), LocalDate.now()).getYears();
                model.addAttribute("edad", edad);
            }
        }

        model.addAttribute("tickets", soporteRepository.findAll());

        Map<Integer, String> sedeMap = new HashMap<>();
        for (SedesGW sede : sedesRepository.findAll()) {
            sedeMap.put(sede.getIdSede(), sede.getNombreSede());
        }
        model.addAttribute("sedeMap", sedeMap);

        Map<Integer, String> maquinaMap = new HashMap<>();
        for (MaquinasGW maq : maquinasRepository.findAll()) {
            maquinaMap.put(maq.getIdMaquina(), maq.getNombreModelo());
        }
        model.addAttribute("maquinaMap", maquinaMap);

        return "soporte";
    }
}

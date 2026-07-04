package com.gymwork.backend;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
public class ValidacionController {

    private final ClientesGWRepository clientesRepository;
    private final PagosGWRepository pagosRepository;
    private final RegistroDGWRepository registroRepository;

    public ValidacionController(ClientesGWRepository clientesRepository,
                                 PagosGWRepository pagosRepository,
                                 RegistroDGWRepository registroRepository) {
        this.clientesRepository = clientesRepository;
        this.pagosRepository = pagosRepository;
        this.registroRepository = registroRepository;
    }

    @GetMapping("/validacion")
    public String validacion(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        List<ClientesGW> todosClientes = clientesRepository.findAll();
        List<PagosGW> todosPagos = pagosRepository.findAll();
        LocalDate hoy = LocalDate.now();
        List<ClientesGW> activos = new ArrayList<>();
        Map<String, String> planesMap = new HashMap<>();

        for (ClientesGW cliente : todosClientes) {
            boolean activo = false;
            for (PagosGW pago : todosPagos) {
                if (pago.getCodigoCliente() != null
                        && pago.getCodigoCliente().equals(cliente.getCodigo())
                        && pago.getFechaPago() != null) {
                    long dias = ChronoUnit.DAYS.between(pago.getFechaPago().toLocalDate(), hoy);
                    if (dias <= 30) {
                        activo = true;
                        planesMap.put(cliente.getCodigo(), pago.getTipoPlan());
                        break;
                    }
                }
            }
            if (activo) {
                activos.add(cliente);
            }
        }

        DatosAdminGW perfil = (DatosAdminGW) session.getAttribute("perfil");
        Integer idSede = (perfil != null) ? perfil.getIdSede() : null;
        model.addAttribute("idSede", idSede);
        model.addAttribute("clientesActivos", activos);
        model.addAttribute("planesMap", planesMap);
        return "validacion";
    }

    @PostMapping("/validacion/asistencia")
    public String registrarAsistencia(@RequestParam String codigoCliente,
                                       @RequestParam String horaIngreso,
                                       @RequestParam String horaSalida,
                                       HttpSession session,
                                       RedirectAttributes redirectAttrs) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        var clienteOpt = clientesRepository.findById(codigoCliente);
        if (clienteOpt.isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "Cliente no encontrado");
            return "redirect:/validacion";
        }

        Integer idSede = clienteOpt.get().getIdSede();
        if (idSede == null) {
            redirectAttrs.addFlashAttribute("error", "El cliente no tiene una sede asignada");
            return "redirect:/validacion";
        }

        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
            RegistroDGW registro = new RegistroDGW();
            registro.setFecha(LocalDate.now());
            registro.setHora(LocalTime.now());
            registro.setCodigoCliente(codigoCliente);
            registro.setIdSede(idSede);
            registro.setCodIns(1);
            registro.setHoraIngreso(LocalTime.parse(horaIngreso, fmt));
            registro.setHoraSalida(LocalTime.parse(horaSalida, fmt));
            registroRepository.save(registro);

            redirectAttrs.addFlashAttribute("exito",
                "Asistencia registrada para el cliente " + codigoCliente);
        } catch (DateTimeParseException e) {
            redirectAttrs.addFlashAttribute("error",
                "Formato de hora inv\u00E1lido. Use HH:mm");
        }

        return "redirect:/validacion";
    }
}

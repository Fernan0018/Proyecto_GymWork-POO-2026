package com.gymwork.backend;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class SoporteController {

    private final SoporteGWRepository soporteRepository;
    private final SedesGWRepository sedesRepository;
    private final MaquinasGWRepository maquinasRepository;
    private final DatosAdminGWRepository datosAdminRepository;
    private final TiemposTicketGWRepository tiemposTicketRepository;
    private final RegistroTiempoPromGWRepository registroPromRepository;

    public SoporteController(SoporteGWRepository soporteRepository, SedesGWRepository sedesRepository, MaquinasGWRepository maquinasRepository, DatosAdminGWRepository datosAdminRepository, TiemposTicketGWRepository tiemposTicketRepository, RegistroTiempoPromGWRepository registroPromRepository) {
        this.soporteRepository = soporteRepository;
        this.sedesRepository = sedesRepository;
        this.maquinasRepository = maquinasRepository;
        this.datosAdminRepository = datosAdminRepository;
        this.tiemposTicketRepository = tiemposTicketRepository;
        this.registroPromRepository = registroPromRepository;
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

        procesarCierreDia();
        actualizarPromedioDiario();

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

        model.addAttribute("sedes", sedesRepository.findAll());
        model.addAttribute("maquinas", maquinasRepository.findAll());

        Map<String, Long> categoriasCount = soporteRepository.findAll().stream()
            .collect(Collectors.groupingBy(SoporteGW::getCategoria, Collectors.counting()));
        model.addAttribute("categoriasCount", categoriasCount);

        LocalDate hoy = LocalDate.now();
        Optional<RegistroTiempoPromGW> hoyReg = registroPromRepository.findById(hoy);
        if (hoyReg.isPresent()) {
            model.addAttribute("ultimoPromedio", hoyReg.get().getPromedioMinutosResolucion());
            model.addAttribute("ultimaFecha", hoy);
        }

        return "soporte";
    }

    @PostMapping("/soporte/crear")
    public String crearTicket(
            @RequestParam("asunto") String asunto,
            @RequestParam("remitente") String remitente,
            @RequestParam("categoria") String categoria,
            @RequestParam(value = "idSede", required = false) Integer idSede,
            @RequestParam(value = "idMaquina", required = false) Integer idMaquina,
            @RequestParam("prioridad") String prioridad) {
        SoporteGW ticket = new SoporteGW();
        ticket.setAsuntoTicket(asunto);
        ticket.setRemitente(remitente);
        ticket.setCategoria(categoria);
        ticket.setIdSede(idSede);
        ticket.setIdMaquinas(idMaquina);
        ticket.setPrioridad(prioridad);
        ticket.setEstadoTicket("Abierto");
        soporteRepository.save(ticket);
        return "redirect:/soporte";
    }

    @PostMapping("/soporte/modificar")
    public String modificarTicket(
            @RequestParam("idTicket") Integer idTicket,
            @RequestParam("estadoTicket") String estadoTicket) {
        Optional<SoporteGW> opt = soporteRepository.findById(idTicket);
        if (opt.isPresent()) {
            SoporteGW ticket = opt.get();
            ticket.setEstadoTicket(estadoTicket);
            soporteRepository.save(ticket);

            if ("En proceso".equals(estadoTicket)) {
                Optional<TiemposTicketGW> tOpt = tiemposTicketRepository.findById(idTicket);
                TiemposTicketGW tt = tOpt.orElseGet(TiemposTicketGW::new);
                tt.setIdTicket(idTicket);
                tt.setTiempoInicio(LocalTime.now());
                tt.setDiaResolucion(LocalDate.now());
                tt.setTiempoFin(null);
                tt.setMinutosResolucion(null);
                tiemposTicketRepository.save(tt);
            } else if ("Resuelto".equals(estadoTicket)) {
                LocalTime ahora = LocalTime.now();
                Optional<TiemposTicketGW> tOpt = tiemposTicketRepository.findById(idTicket);
                if (tOpt.isPresent()) {
                    TiemposTicketGW tt = tOpt.get();
                    tt.setTiempoFin(ahora);
                    tt.setDiaResolucion(LocalDate.now());
                    if (tt.getTiempoInicio() != null) {
                        long minutos = ChronoUnit.MINUTES.between(tt.getTiempoInicio(), ahora);
                        tt.setMinutosResolucion((int) Math.max(minutos, 0));
                    }
                    tiemposTicketRepository.save(tt);
                } else {
                    TiemposTicketGW tt = new TiemposTicketGW();
                    tt.setIdTicket(idTicket);
                    tt.setTiempoInicio(ahora);
                    tt.setTiempoFin(ahora);
                    tt.setDiaResolucion(LocalDate.now());
                    tt.setMinutosResolucion(0);
                    tiemposTicketRepository.save(tt);
                }
                actualizarPromedioDiario();
            }
        }
        return "redirect:/soporte";
    }

    private void actualizarPromedioDiario() {
        LocalDate hoy = LocalDate.now();
        List<TiemposTicketGW> todos = tiemposTicketRepository.findAll();
        List<Integer> minutos = todos.stream()
            .filter(tt -> tt.getMinutosResolucion() != null && hoy.equals(tt.getDiaResolucion()))
            .map(TiemposTicketGW::getMinutosResolucion)
            .collect(Collectors.toList());
        if (minutos.isEmpty()) {
            registroPromRepository.findById(hoy).ifPresent(reg -> registroPromRepository.delete(reg));
            return;
        }

        double promedio = minutos.stream().collect(Collectors.averagingInt(m -> m));
        RegistroTiempoPromGW reg = registroPromRepository.findById(hoy)
            .orElseGet(RegistroTiempoPromGW::new);
        reg.setFecha(hoy);
        reg.setPromedioMinutosResolucion(promedio);
        registroPromRepository.save(reg);
    }

    private void procesarCierreDia() {
        LocalDate hoy = LocalDate.now();
        List<SoporteGW> resueltos = soporteRepository.findByEstadoTicket("Resuelto");
        if (resueltos.isEmpty()) return;

        Map<LocalDate, List<Integer>> minutosPorFecha = new HashMap<>();
        List<Integer> idsAProcesar = new ArrayList<>();

        for (SoporteGW ticket : resueltos) {
            Integer id = ticket.getIdTicket();
            Optional<TiemposTicketGW> tOpt = tiemposTicketRepository.findById(id);
            if (tOpt.isPresent()) {
                TiemposTicketGW tt = tOpt.get();
                if (tt.getDiaResolucion() != null && tt.getDiaResolucion().isBefore(hoy)) {
                    idsAProcesar.add(id);
                    if (tt.getMinutosResolucion() != null) {
                        minutosPorFecha
                            .computeIfAbsent(tt.getDiaResolucion(), k -> new ArrayList<>())
                            .add(tt.getMinutosResolucion());
                    }
                }
            }
        }

        if (idsAProcesar.isEmpty()) return;

        for (Map.Entry<LocalDate, List<Integer>> entry : minutosPorFecha.entrySet()) {
            LocalDate fecha = entry.getKey();
            List<Integer> minutos = entry.getValue();
            double promedio = minutos.stream().collect(Collectors.averagingInt(m -> m));

            RegistroTiempoPromGW reg = registroPromRepository.findById(fecha)
                .orElseGet(RegistroTiempoPromGW::new);
            reg.setFecha(fecha);
            reg.setPromedioMinutosResolucion(promedio);
            registroPromRepository.save(reg);
        }

        tiemposTicketRepository.deleteAllById(idsAProcesar);
        soporteRepository.deleteAllById(idsAProcesar);
    }
}

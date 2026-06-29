package com.gymwork.backend;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class AdministracionController {

    @Autowired
    private ClientesGWRepository clientesRepository;

    @Autowired
    private PagosGWRepository pagosRepository;

    @Autowired
    private UsuariosGWRepository usuariosRepository;

    @Autowired
    private DatosAdminGWRepository datosAdminRepository;

    @GetMapping("/administracion")
    public String verAdministracion(HttpSession session, Model model) {
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

        long totalClientes = clientesRepository.count();
        model.addAttribute("totalClientes", totalClientes);

        List<PagosGW> todosLosPagos = pagosRepository.findAll();
        List<ClientesGW> todosLosClientes = clientesRepository.findAll();
        List<UsuariosGW> todosLosUsuarios = usuariosRepository.findAll();

        int gananciaTotal = 0;
        long basicosActivos = 0;
        long fitsActivos = 0;
        long premiumsActivos = 0;
        LocalDate hoy = LocalDate.now();

        for (PagosGW pago : todosLosPagos) {
            String plan = pago.getTipoPlan();

            if ("100".equals(plan)) gananciaTotal += 90;
            else if ("010".equals(plan)) gananciaTotal += 110;
            else if ("001".equals(plan)) gananciaTotal += 130;

            if (pago.getFechaPago() != null) {
                LocalDate fechaPagoLocal = pago.getFechaPago().toLocalDate();
                long diasPasados = ChronoUnit.DAYS.between(fechaPagoLocal, hoy);

                if (diasPasados <= 30) {
                    if ("100".equals(plan)) basicosActivos++;
                    else if ("010".equals(plan)) fitsActivos++;
                    else if ("001".equals(plan)) premiumsActivos++;
                }
            }
        }

        Map<String, String> estadosMap = new HashMap<>();
        for (ClientesGW cliente : todosLosClientes) {
            boolean activo = false;
            for (PagosGW pago : todosLosPagos) {
                if (pago.getCodigoCliente() != null && pago.getCodigoCliente().equals(cliente.getCodigo())
                        && pago.getFechaPago() != null) {
                    long dias = ChronoUnit.DAYS.between(pago.getFechaPago().toLocalDate(), hoy);
                    if (dias <= 30) {
                        activo = true;
                        break;
                    }
                }
            }
            estadosMap.put(cliente.getCodigo(), activo ? "Activo" : "Vencido");
        }

        model.addAttribute("gananciaTotal", gananciaTotal);
        model.addAttribute("basicosActivos", basicosActivos);
        model.addAttribute("fitsActivos", fitsActivos);
        model.addAttribute("premiumsActivos", premiumsActivos);
        model.addAttribute("usuarios", todosLosUsuarios);
        model.addAttribute("clientes", todosLosClientes);
        model.addAttribute("pagos", todosLosPagos);
        model.addAttribute("estadosMap", estadosMap);

        return "administracion";
    }

    @PostMapping("/administracion/renovar")
    public String renovarMembresia(@RequestParam("idPagoSeleccionado") Long idPagoSeleccionado) {
        Optional<PagosGW> optPago = pagosRepository.findById(idPagoSeleccionado);
        if (optPago.isPresent()) {
            PagosGW pagoOriginal = optPago.get();
            PagosGW nuevoPago = new PagosGW(pagoOriginal.getCodigoCliente(), pagoOriginal.getTipoPlan());
            pagosRepository.save(nuevoPago);
        }
        return "redirect:/administracion";
    }

    @GetMapping("/administracion/registrar-admin")
    public String mostrarFormularioRegistrar(Model model) {
        model.addAttribute("usuario", new UsuariosGW());
        model.addAttribute("modo", "Registrar");
        model.addAttribute("accion", "registrar-admin");
        return "admin-form";
    }

    @PostMapping("/administracion/registrar-admin")
    public String guardarAdmin(UsuariosGW usuario) {
        usuariosRepository.save(usuario);
        return "redirect:/administracion";
    }

    @GetMapping("/administracion/modificar-admin")
    public String mostrarFormularioModificar(@RequestParam("id") Integer id, Model model) {
        Optional<UsuariosGW> optUsuario = usuariosRepository.findById(id);
        if (optUsuario.isPresent()) {
            model.addAttribute("usuario", optUsuario.get());
        } else {
            model.addAttribute("usuario", new UsuariosGW());
        }
        model.addAttribute("modo", "Modificar");
        model.addAttribute("accion", "modificar-admin");
        return "admin-form";
    }

    @PostMapping("/administracion/modificar-admin")
    public String actualizarAdmin(UsuariosGW usuario) {
        usuariosRepository.save(usuario);
        return "redirect:/administracion";
    }

    @GetMapping("/administracion/eliminar-admin")
    public String eliminarAdmin(@RequestParam("id") Integer id) {
        usuariosRepository.deleteById(id);
        return "redirect:/administracion";
    }
}

package com.gymwork.backend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SuscripcionesController {

    private ClientesGWRepository clientesRepository;

    private PagosGWRepository pagosRepository;

    @GetMapping("/suscripciones")
    public String mostrarFormularioSuscripciones(Model model) {
        model.addAttribute("cliente", new ClientesGW());
        return "suscripciones";
    }

    @PostMapping("/suscripciones/guardar")
    public Object registrarSuscripcionWeb(
            @ModelAttribute("cliente") ClientesGW cliente,
            @RequestParam(value = "ajax", defaultValue = "false") boolean ajax) {
        try {
            cliente.setIdSede(1);
            clientesRepository.save(cliente);

            PagosGW pago = new PagosGW(
                cliente.getCodigo(),
                cliente.getTipoPlan()
            );
            pagosRepository.save(pago);

            if (ajax) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                return ResponseEntity.ok(response);
            }
            return "redirect:/administracion";
        } catch (Exception e) {
            e.printStackTrace();
            if (ajax) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("error", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
            return "redirect:/administracion?error=Error+al+registrar";
        }
    }
}

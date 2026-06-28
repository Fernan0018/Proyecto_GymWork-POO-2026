package com.gymwork.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SuscripcionesController {

    // Aquí inyectamos los accesos a tus dos tablas de la Base de Datos
    @Autowired
    private ClientesGWRepository clientesRepository;

    @Autowired
    private PagosGWRepository pagosRepository;

    // 1. Esta ruta muestra tu formulario web (Sustituye al JFrame visible)
    @GetMapping("/suscripciones")
    public String mostrarFormularioSuscripciones(Model model) {
        model.addAttribute("cliente", new ClientesGW());
        return "suscripciones"; // Buscará tu archivo suscripciones.jsp
    }

    // 2. Esta ruta recibe los datos cuando le das al botón "Aceptar" (AJAX/Fetch)
    @PostMapping("/suscripciones/guardar")
    public ResponseEntity<Map<String, Object>> registrarSuscripcionWeb(@ModelAttribute("cliente") ClientesGW cliente) {
        try {
            // Paso A: Asigna sede por defecto y guarda los datos en la tabla ClientesGW
            cliente.setIdSede(1);
            clientesRepository.save(cliente);

            // Paso B: Genera el registro automático en la tabla PagosGW
            PagosGW pago = new PagosGW(
                cliente.getCodigo(),
                cliente.getTipoPlan()
            );
            pagosRepository.save(pago);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

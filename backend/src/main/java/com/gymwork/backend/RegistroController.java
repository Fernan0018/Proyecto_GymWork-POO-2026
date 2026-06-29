package com.gymwork.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.logging.Logger;
import java.util.logging.Level;

@Controller
public class RegistroController {

    private static final Logger LOG = Logger.getLogger(RegistroController.class.getName());
    private final ClientesGWRepository clientesRepository;

    public RegistroController(ClientesGWRepository clientesRepository) {
        this.clientesRepository = clientesRepository;
    }

    @GetMapping("/registro")
    public String registro(
        @RequestParam(required = false) String buscar,
        Model model){

            LOG.log(Level.INFO, "Buscar = {0}", buscar);
    if (buscar == null || buscar.isBlank()) {
        model.addAttribute("clientes", clientesRepository.findAll());
    } else {
        model.addAttribute(
            "clientes",
            clientesRepository.findByNombreContainingIgnoreCaseOrCodigoContainingIgnoreCase(
                    buscar,
                    buscar
                )
        );
    }

       
        return "registro";
    }

}
package com.gymwork.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistroController {

    @Autowired
    private ClientesGWRepository clientesRepository;

    @GetMapping("/registro")
    public String registro(
        @RequestParam(required = false) String buscar,
        Model model){

            System.out.println("Buscar = " + buscar);
    if (buscar == null || buscar.isBlank()) {
        model.addAttribute("clientes", clientesRepository.findAll());
    } else {
        model.addAttribute(
            "clientes",
            clientesRepository.findByNombresClienteContainingIgnoreCaseOrCodigoClienteContainingIgnoreCase(
                    buscar,
                    buscar
                )
        );
    }

       
        return "registro";
    }

}
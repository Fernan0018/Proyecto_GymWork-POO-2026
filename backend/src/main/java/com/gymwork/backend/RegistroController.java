package com.gymwork.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

@Controller
public class RegistroController {

    private static final Logger LOG = Logger.getLogger(RegistroController.class.getName());
    private final ClientesGWRepository clientesRepository;
    private final PagosGWRepository pagosRepository;

    public RegistroController(ClientesGWRepository clientesRepository, PagosGWRepository pagosRepository) {
        this.clientesRepository = clientesRepository;
        this.pagosRepository = pagosRepository;
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

        List<?> clientesList = (List<?>) model.getAttribute("clientes");
        List<PagosGW> todosLosPagos = pagosRepository.findAll();
        Map<String, String> estadosMap = new HashMap<>();
        LocalDate hoy = LocalDate.now();

        for (Object obj : clientesList) {
            ClientesGW cliente = (ClientesGW) obj;
            boolean activo = false;
            for (PagosGW pago : todosLosPagos) {
                if (pago.getCodigoCliente() != null
                        && pago.getCodigoCliente().equals(cliente.getCodigo())
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

        model.addAttribute("estadosMap", estadosMap);

        
        return "registro";
    }

    @PostMapping("/registro/actualizar")
    public String actualizarCliente(@ModelAttribute ClientesGW cliente,
                                    RedirectAttributes redirect) {

                ClientesGW existente = clientesRepository.findById(cliente.getCodigo())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            existente.setNombre(cliente.getNombre());
            existente.setApellidoPaterno(cliente.getApellidoPaterno());
            existente.setApellidoMaterno(cliente.getApellidoMaterno());
            existente.setFechaNacimiento(cliente.getFechaNacimiento());
            existente.setSexo(cliente.getSexo());
            existente.setCorreo(cliente.getCorreo());
            existente.setTelefono(cliente.getTelefono());
            existente.setTipoPlan(cliente.getTipoPlan());
            existente.setDepartamento(cliente.getDepartamento());
            existente.setCiudad(cliente.getCiudad());
            existente.setCalle(cliente.getCalle());
            existente.setEdad(cliente.getEdad());

            // NO modificar idSede

            clientesRepository.save(existente);

            redirect.addFlashAttribute("exito", "Cliente actualizado correctamente");
            return "redirect:/registro";
            }

    @PostMapping("/registro/eliminar")
    public String eliminarCliente(String codigo, RedirectAttributes redirect) {

            List<PagosGW> pagos = pagosRepository.findAll();
            boolean activo = false;
            for (PagosGW pago : pagos) {
                if (pago.getCodigoCliente() != null
                        && pago.getCodigoCliente().equals(codigo)
                        && pago.getFechaPago() != null) {
                    long dias = ChronoUnit.DAYS.between(pago.getFechaPago().toLocalDate(), LocalDate.now());
                    if (dias <= 30) {
                        activo = true;
                        break;
                    }
                }
            }
            if (activo) {
                redirect.addFlashAttribute("error", "No se puede eliminar un cliente con membres\u00EDa activa");
                return "redirect:/registro";
            }

            try {
                clientesRepository.deleteById(codigo);
                redirect.addFlashAttribute("exito", "Cliente eliminado");

            } catch (Exception e) {
                redirect.addFlashAttribute(
                    "error",
                    "No se puede eliminar el cliente porque tiene pagos registrados."
                );
            }

            return "redirect:/registro";
    }

    @GetMapping("/registro/reporte/pdf")
    public ResponseEntity<byte[]> generarReporteClientes() {
        List<ClientesGW> clientes = clientesRepository.findAll();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream cs = new PDPageContentStream(document, page);
            int y = 800;

            cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 20);
            cs.beginText();
            cs.newLineAtOffset(50, y);
            cs.showText("REPORTE DE CLIENTES - GYMWORK");
            cs.endText();
            y -= 25;

            cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 11);
            cs.beginText();
            cs.newLineAtOffset(50, y);
            cs.showText("Generado: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            cs.endText();
            y -= 25;

            cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 11);
            cs.beginText();
            cs.newLineAtOffset(50, y);
            cs.showText("Total clientes: " + clientes.size());
            cs.endText();
            y -= 25;

            String[] headers = {"C\u00f3digo", "Nombre", "Apellidos", "Edad", "Plan", "Correo", "Tel\u00e9fono", "F.Registro"};
            float[] colWidths = {55, 75, 80, 30, 60, 100, 70, 65};
            float xStart = 50;
            float tableWidth = 0;
            for (float w : colWidths) tableWidth += w;

            cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 8);
            cs.setNonStrokingColor(
                245f / 255f,
                245f / 255f,
                245f / 255f
            );
            cs.addRect(xStart, y - 12, tableWidth, 14);
            cs.fill();
            cs.setNonStrokingColor(0, 0, 0);

            float x = xStart;
            cs.setNonStrokingColor(0, 0, 0);
            for (int i = 0; i < headers.length; i++) {
                cs.beginText();
                cs.newLineAtOffset(x + 2, y - 9);
                cs.showText(headers[i]);
                cs.endText();
                x += colWidths[i];
            }
            y -= 18;

            cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 7);

            for (ClientesGW c : clientes) {
                if (y < 50) {
                    cs.close();
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    cs = new PDPageContentStream(document, page);
                    y = 800;

                    cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 8);
                    x = xStart;
                    for (int i = 0; i < headers.length; i++) {
                        cs.beginText();
                        cs.newLineAtOffset(x + 2, y - 9);
                        cs.showText(headers[i]);
                        cs.endText();
                        x += colWidths[i];
                    }
                    y -= 18;
                    cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 7);
                }

                int edad = -1;
                if (c.getFechaNacimiento() != null) {
                    edad = Period.between(c.getFechaNacimiento().toLocalDate(), LocalDate.now()).getYears();
                }

                String apellidos = (c.getApellidoPaterno() != null ? c.getApellidoPaterno() : "")
                                 + " " + (c.getApellidoMaterno() != null ? c.getApellidoMaterno() : "");
                String edadStr = edad >= 0 ? String.valueOf(edad) : "-";

                if (y % 4 == 0) {
                    cs.setNonStrokingColor(
                        245f / 255f,
                        245f / 255f,
                        245f / 255f
                    );
                    cs.addRect(xStart, y - 12, tableWidth, 14);
                    cs.fill();
                }
                cs.setNonStrokingColor(0, 0, 0);

                String[] rowData = {
                    c.getCodigo() != null ? c.getCodigo() : "",
                    c.getNombre() != null ? c.getNombre() : "",
                    apellidos,
                    edadStr,
                    c.getTipoPlan() != null ? mapPlanNombre(c.getTipoPlan()) : "",
                    c.getCorreo() != null ? c.getCorreo() : "",
                    c.getTelefono() != null ? c.getTelefono() : "",
                    c.getFechaRegistro() != null ? c.getFechaRegistro().toString() : ""
                };

                x = xStart;
                for (int i = 0; i < rowData.length; i++) {
                    cs.beginText();
                    cs.newLineAtOffset(x + 2, y - 9);
                    cs.showText(rowData[i]);
                    cs.endText();
                    x += colWidths[i];
                }
                y -= 14;
            }

            cs.close();
            document.save(baos);
            document.close();

            byte[] pdfBytes = baos.toByteArray();
            HttpHeaders headersResp = new HttpHeaders();
            headersResp.setContentType(MediaType.APPLICATION_PDF);
            headersResp.setContentDispositionFormData("attachment", "reporte_clientes_" + LocalDate.now() + ".pdf");

            return ResponseEntity.ok().headers(headersResp).body(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    private String mapPlanNombre(String tipoPlan) {
        if (tipoPlan == null) return "";
        switch (tipoPlan) {
            case "001": return "Paquete Fit";
            case "010": return "Paquete B\u00E1sico";
            case "100": return "Plan Premium";
            default: return tipoPlan;
        }
    }

}
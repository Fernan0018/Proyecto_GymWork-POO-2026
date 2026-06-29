package com.gymwork.backend;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
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
        long mantenimiento = maquinasRepository.countByEstadoMaquina("En Reparaci\u00F3n");
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

        UsuariosGW usuario = usuariosRepository.findByUsername(username);

        if (usuario == null || !usuario.getPassword().equals(deletePassword)) {
            redirect.addFlashAttribute("error", "Contraseña de eliminación incorrecta");
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

    @GetMapping("/maquinas/reporte/pdf")
    public ResponseEntity<byte[]> generarReporteMaquinas(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(401).build();
        }

        List<MaquinasGW> maquinas = maquinasRepository.findAll();
        Map<Integer, String> sedeMap = new HashMap<>();
        for (SedesGW sede : sedesRepository.findAll()) {
            sedeMap.put(sede.getIdSede(), sede.getNombreSede());
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream cs = new PDPageContentStream(document, page);
            int y = 800;

            cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 20);
            cs.beginText();
            cs.newLineAtOffset(50, y);
            cs.showText("REPORTE DE MAQUINAS - GYMWORK");
            cs.endText();
            y -= 25;

            cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 11);
            cs.beginText();
            cs.newLineAtOffset(50, y);
            cs.showText("Generado: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            cs.endText();
            y -= 25;

            long total = maquinas.size();
            long operativas = maquinasRepository.countByEstadoMaquina("Operativa");
        long mantenimiento = maquinasRepository.countByEstadoMaquina("En Reparaci\u00F3n");
            long inactivas = maquinasRepository.countByEstadoMaquina("Inactiva");

            cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 11);
            cs.beginText();
            cs.newLineAtOffset(50, y);
            cs.showText("Total: " + total + " | Operativas: " + operativas + " | En Reparaci\u00F3n: " + mantenimiento + " | Inactivas: " + inactivas);
            cs.endText();
            y -= 30;

            String[] headers = {"ID", "Nombre/Modelo", "Sede", "Ultimo Mantenimiento", "Estado"};
            float[] colWidths = {30, 150, 120, 110, 100};
            float xStart = 50;
            float tableWidth = 0;
            for (float w : colWidths) tableWidth += w;

            cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 8);
            cs.setNonStrokingColor(245f / 255f, 245f / 255f, 245f / 255f);
            cs.addRect(xStart, y - 12, tableWidth, 14);
            cs.fill();
            cs.setNonStrokingColor(0, 0, 0);

            float x = xStart;
            for (int i = 0; i < headers.length; i++) {
                cs.beginText();
                cs.newLineAtOffset(x + 2, y - 9);
                cs.showText(headers[i]);
                cs.endText();
                x += colWidths[i];
            }
            y -= 18;

            cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 7);

            for (MaquinasGW m : maquinas) {
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

                String[] rowData = {
                    String.valueOf(m.getIdMaquina()),
                    m.getNombreModelo() != null ? m.getNombreModelo() : "",
                    m.getIdSede() != null ? sedeMap.getOrDefault(m.getIdSede(), "") : "",
                    m.getUltimoMantenimiento() != null ? m.getUltimoMantenimiento().toString() : "-",
                    m.getEstadoMaquina() != null ? m.getEstadoMaquina() : ""
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
            headersResp.setContentDispositionFormData("attachment", "reporte_maquinas_" + LocalDate.now() + ".pdf");

            return ResponseEntity.ok().headers(headersResp).body(pdfBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    private boolean isValidEstado(String estado) {
        return estado != null && (estado.equals("Operativa") || estado.equals("En Reparaci\u00F3n") || estado.equals("Inactiva"));
    }

}

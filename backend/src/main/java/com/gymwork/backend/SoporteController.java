package com.gymwork.backend;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
    public String soporte(
            HttpSession session,
            Model model,
            @RequestParam(value = "categoria", required = false) String categoria,
            @RequestParam(value = "prioridad", required = false) String prioridad,
            @RequestParam(value = "estado", required = false) String estado,
            @PageableDefault(size = 10, sort = "idTicket", direction = Sort.Direction.DESC) Pageable pageable) {
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

        Specification<SoporteGW> spec = (root, query, cb) -> cb.conjunction();
        if (categoria != null && !categoria.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("categoria"), categoria));
        }
        if (prioridad != null && !prioridad.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("prioridad"), prioridad));
        }
        if (estado != null && !estado.isEmpty()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("estadoTicket"), estado));
        }

        Page<SoporteGW> ticketPage = soporteRepository.findAll(spec, pageable);
        model.addAttribute("ticketPage", ticketPage);
        model.addAttribute("tickets", ticketPage.getContent());

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

    @GetMapping("/soporte/reporte/diario")
    public ResponseEntity<byte[]> generarReporteDiario() {
        LocalDate hoy = LocalDate.now();

        List<TiemposTicketGW> tiemposHoy = tiemposTicketRepository.findByDiaResolucion(hoy);
        Map<Integer, TiemposTicketGW> tiempoMap = tiemposHoy.stream()
            .collect(Collectors.toMap(TiemposTicketGW::getIdTicket, t -> t));
        List<SoporteGW> ticketsResueltos = soporteRepository.findAllById(tiempoMap.keySet());
        Map<Integer, SoporteGW> ticketMap = ticketsResueltos.stream()
            .collect(Collectors.toMap(SoporteGW::getIdTicket, t -> t));

        Map<Integer, String> sedeMap = new HashMap<>();
        for (SedesGW sede : sedesRepository.findAll()) {
            sedeMap.put(sede.getIdSede(), sede.getNombreSede());
        }

        List<SoporteGW> activos = soporteRepository.findAll().stream()
            .filter(t -> "Abierto".equals(t.getEstadoTicket()) || "En proceso".equals(t.getEstadoTicket()))
            .collect(Collectors.toList());

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream cs = new PDPageContentStream(document, page);
            try {
                int y = 800;

                cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 22);
                cs.beginText();
                cs.newLineAtOffset(50, y);
                cs.showText("REPORTE DIARIO - MESA DE AYUDA");
                cs.endText();
                y -= 25;

                cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                cs.beginText();
                cs.newLineAtOffset(50, y);
                cs.showText("Fecha: " + hoy.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                cs.endText();
                y -= 30;

                int resueltos = tiemposHoy.size();
                double promedio = tiemposHoy.stream()
                    .filter(tt -> tt.getMinutosResolucion() != null)
                    .mapToInt(TiemposTicketGW::getMinutosResolucion)
                    .average().orElse(0);
                int min = tiemposHoy.stream()
                    .filter(tt -> tt.getMinutosResolucion() != null)
                    .mapToInt(TiemposTicketGW::getMinutosResolucion)
                    .min().orElse(0);
                int max = tiemposHoy.stream()
                    .filter(tt -> tt.getMinutosResolucion() != null)
                    .mapToInt(TiemposTicketGW::getMinutosResolucion)
                    .max().orElse(0);

                cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                cs.beginText();
                cs.newLineAtOffset(50, y);
                cs.showText("Resumen");
                cs.endText();
                y -= 20;

                cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 11);
                String[] resumenLines = {
                    "Tickets resueltos: " + resueltos,
                    "Tiempo promedio: " + String.format("%.2f", promedio) + " min",
                    "M\u00ednimo: " + min + " min  |  M\u00e1ximo: " + max + " min",
                    "Tickets activos pendientes: " + activos.size()
                };
                for (String line : resumenLines) {
                    cs.beginText();
                    cs.newLineAtOffset(60, y);
                    cs.showText(line);
                    cs.endText();
                    y -= 18;
                }
                y -= 15;

                if (!tiemposHoy.isEmpty()) {
                    BufferedImage chartImage = dibujarGraficaBarras(tiemposHoy, ticketMap);
                    PDImageXObject pdImage = LosslessFactory.createFromImage(document, chartImage);
                    cs.drawImage(pdImage, 50, y - 280, 500, 280);
                    y -= 300;
                }

                if (!tiemposHoy.isEmpty()) {
                    cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                    cs.beginText();
                    cs.newLineAtOffset(50, y);
                    cs.showText("Tickets resueltos del d\u00eda");
                    cs.endText();
                    y -= 20;

                    String[] headers = {"ID", "Asunto", "Categor\u00eda", "Minutos"};
                    float[] colWidths = {35, 250, 120, 70};
                    float xStart = 50;

                    cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 9);
                    float x = xStart;
                    for (int i = 0; i < headers.length; i++) {
                        cs.beginText();
                        cs.newLineAtOffset(x, y);
                        cs.showText(headers[i]);
                        cs.endText();
                        x += colWidths[i];
                    }
                    y -= 16;

                    cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9);
                    for (TiemposTicketGW tt : tiemposHoy) {
                        SoporteGW ticket = ticketMap.get(tt.getIdTicket());
                        if (y < 50) {
                            cs.close();
                            page = new PDPage(PDRectangle.A4);
                            document.addPage(page);
                            cs = new PDPageContentStream(document, page);
                            y = 800;
                        }
                        x = xStart;
                        cs.beginText();
                        cs.newLineAtOffset(x, y);
                        cs.showText(String.valueOf(tt.getIdTicket()));
                        cs.endText();
                        x += colWidths[0];

                        String asunto = ticket != null ? ticket.getAsuntoTicket() : "";
                        if (asunto.length() > 40) asunto = asunto.substring(0, 37) + "...";
                        cs.beginText();
                        cs.newLineAtOffset(x, y);
                        cs.showText(asunto);
                        cs.endText();
                        x += colWidths[1];

                        cs.beginText();
                        cs.newLineAtOffset(x, y);
                        cs.showText(ticket != null && ticket.getCategoria() != null ? ticket.getCategoria() : "");
                        cs.endText();
                        x += colWidths[2];

                        cs.beginText();
                        cs.newLineAtOffset(x, y);
                        cs.showText(String.valueOf(tt.getMinutosResolucion() != null ? tt.getMinutosResolucion() : 0));
                        cs.endText();
                        y -= 14;
                    }
                    y -= 20;
                }

                if (!activos.isEmpty()) {
                    if (y < 100) {
                        cs.close();
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        cs = new PDPageContentStream(document, page);
                        y = 800;
                    }

                    cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                    cs.beginText();
                    cs.newLineAtOffset(50, y);
                    cs.showText("Tickets activos pendientes");
                    cs.endText();
                    y -= 20;

                    String[] actHeaders = {"ID", "Asunto", "Prioridad", "Estado"};
                    float[] actWidths = {35, 250, 100, 100};
                    float xStart = 50;

                    cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 9);
                    float x = xStart;
                    for (int i = 0; i < actHeaders.length; i++) {
                        cs.beginText();
                        cs.newLineAtOffset(x, y);
                        cs.showText(actHeaders[i]);
                        cs.endText();
                        x += actWidths[i];
                    }
                    y -= 16;

                    cs.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 9);
                    for (SoporteGW ticket : activos) {
                        if (y < 50) {
                            cs.close();
                            page = new PDPage(PDRectangle.A4);
                            document.addPage(page);
                            cs = new PDPageContentStream(document, page);
                            y = 800;
                        }
                        x = xStart;
                        cs.beginText();
                        cs.newLineAtOffset(x, y);
                        cs.showText(String.valueOf(ticket.getIdTicket()));
                        cs.endText();
                        x += actWidths[0];

                        String asunto = ticket.getAsuntoTicket();
                        if (asunto.length() > 40) asunto = asunto.substring(0, 37) + "...";
                        cs.beginText();
                        cs.newLineAtOffset(x, y);
                        cs.showText(asunto);
                        cs.endText();
                        x += actWidths[1];

                        cs.beginText();
                        cs.newLineAtOffset(x, y);
                        cs.showText(ticket.getPrioridad() != null ? ticket.getPrioridad() : "");
                        cs.endText();
                        x += actWidths[2];

                        cs.beginText();
                        cs.newLineAtOffset(x, y);
                        cs.showText(ticket.getEstadoTicket());
                        cs.endText();
                        y -= 14;
                    }
                }
            } finally {
                cs.close();
            }

            document.save(baos);
            document.close();

            byte[] pdfBytes = baos.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition", "attachment; filename=reporte_diario_" + hoy + ".pdf");

            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private BufferedImage dibujarGraficaBarras(List<TiemposTicketGW> tiempos, Map<Integer, SoporteGW> ticketMap) {
        int ancho = 600, alto = 320;
        BufferedImage img = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, ancho, alto);

        int marginLeft = 55, marginBottom = 50, marginTop = 45, marginRight = 15;
        int chartW = ancho - marginLeft - marginRight;
        int chartH = alto - marginTop - marginBottom;

        List<Integer> valores = tiempos.stream()
            .map(tt -> tt.getMinutosResolucion() != null ? tt.getMinutosResolucion() : 0)
            .collect(Collectors.toList());
        int maxVal = Math.max(valores.stream().mapToInt(v -> v).max().orElse(1), 1);

        g.setColor(new Color(30, 30, 30));
        g.setFont(new Font("SansSerif", Font.BOLD, 13));
        String titulo = "Tiempo de resoluci\u00f3n por ticket";
        int tituloW = g.getFontMetrics().stringWidth(titulo);
        g.drawString(titulo, (ancho - tituloW) / 2, 22);

        g.setColor(Color.BLACK);
        g.drawLine(marginLeft, marginTop, marginLeft, marginTop + chartH);
        g.drawLine(marginLeft, marginTop + chartH, marginLeft + chartW, marginTop + chartH);

        g.setFont(new Font("SansSerif", Font.PLAIN, 8));
        for (int i = 0; i <= 4; i++) {
            int val = (int) Math.round((double) (maxVal * i) / 4);
            int y = marginTop + chartH - (chartH * i) / 4;
            g.setColor(new Color(220, 220, 220));
            g.drawLine(marginLeft, y, marginLeft + chartW, y);
            g.setColor(Color.BLACK);
            String yLabel = val + " min";
            g.drawString(yLabel, marginLeft - g.getFontMetrics().stringWidth(yLabel) - 4, y + 3);
        }

        int n = valores.size();
        int barWidth = Math.min(50, (chartW - 30) / Math.max(n, 1));
        int spacing = n > 1 ? Math.min(20, (chartW - 30 - barWidth * n) / (n - 1)) : 0;

        Color[] colores = {new Color(252, 213, 53), new Color(0, 123, 255), new Color(40, 167, 69),
                           new Color(220, 53, 69), new Color(253, 126, 20)};

        for (int i = 0; i < n; i++) {
            int barH = (int) ((double) valores.get(i) / maxVal * chartH);
            int x = marginLeft + 15 + i * (barWidth + spacing);
            int y = marginTop + chartH - barH;

            g.setColor(colores[i % colores.length]);
            g.fillRect(x, y, barWidth, barH);
            g.setColor(new Color(60, 60, 60));
            g.drawRect(x, y, barWidth, barH);

            g.setColor(new Color(30, 30, 30));
            g.setFont(new Font("SansSerif", Font.BOLD, 10));
            String valStr = String.valueOf(valores.get(i));
            int valW = g.getFontMetrics().stringWidth(valStr);
            g.drawString(valStr, x + (barWidth - valW) / 2, y - 4);

            g.setColor(Color.BLACK);
            g.setFont(new Font("SansSerif", Font.PLAIN, 8));
            String label = "T" + String.valueOf(tiempos.get(i).getIdTicket());
            int labelW = g.getFontMetrics().stringWidth(label);
            g.drawString(label, x + (barWidth - labelW) / 2, marginTop + chartH + 14);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.PLAIN, 8));
        g.drawString("Ticket", (ancho - g.getFontMetrics().stringWidth("Ticket")) / 2, alto - 5);

        g.dispose();
        return img;
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
                        if (minutos < 0) {
                            minutos += 1440;
                        }
                        minutos = Math.min(minutos, 1440);
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
        List<Integer> idsSinRegistro = new ArrayList<>();

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
            } else {
                idsSinRegistro.add(id);
            }
        }

        if (idsAProcesar.isEmpty() && idsSinRegistro.isEmpty()) return;

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

        if (!idsAProcesar.isEmpty()) {
            tiemposTicketRepository.deleteAllById(idsAProcesar);
            soporteRepository.deleteAllById(idsAProcesar);
        }

        if (!idsSinRegistro.isEmpty()) {
            soporteRepository.deleteAllById(idsSinRegistro);
        }
    }
}

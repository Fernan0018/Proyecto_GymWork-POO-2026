package com.gymwork.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "Historial_SoporteGW")
public class HistorialSoporteGW {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Historial")
    private Integer idHistorial;

    @Column(name = "ID_Ticket")
    private Integer idTicket;

    @Column(name = "Asunto_Ticket")
    private String asuntoTicket;

    @Column(name = "Remitente")
    private String remitente;

    @Column(name = "Categoria")
    private String categoria;

    @Column(name = "Id_Maquinas")
    private Integer idMaquinas;

    @Column(name = "Id_Sede")
    private Integer idSede;

    @Column(name = "Prioridad")
    private String prioridad;

    @Column(name = "Estado_Ticket")
    private String estadoTicket;

    @Column(name = "Tiempo_inicio")
    private LocalTime tiempoInicio;

    @Column(name = "Tiempo_fin")
    private LocalTime tiempoFin;

    @Column(name = "Dia_Resolucion")
    private LocalDate diaResolucion;

    @Column(name = "Minutos_Resolucion")
    private Integer minutosResolucion;

    @Column(name = "Fecha_Archivado")
    private LocalDateTime fechaArchivado;

    public HistorialSoporteGW() {}

    public Integer getIdHistorial() { return idHistorial; }
    public void setIdHistorial(Integer idHistorial) { this.idHistorial = idHistorial; }

    public Integer getIdTicket() { return idTicket; }
    public void setIdTicket(Integer idTicket) { this.idTicket = idTicket; }

    public String getAsuntoTicket() { return asuntoTicket; }
    public void setAsuntoTicket(String asuntoTicket) { this.asuntoTicket = asuntoTicket; }

    public String getRemitente() { return remitente; }
    public void setRemitente(String remitente) { this.remitente = remitente; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Integer getIdMaquinas() { return idMaquinas; }
    public void setIdMaquinas(Integer idMaquinas) { this.idMaquinas = idMaquinas; }

    public Integer getIdSede() { return idSede; }
    public void setIdSede(Integer idSede) { this.idSede = idSede; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public String getEstadoTicket() { return estadoTicket; }
    public void setEstadoTicket(String estadoTicket) { this.estadoTicket = estadoTicket; }

    public LocalTime getTiempoInicio() { return tiempoInicio; }
    public void setTiempoInicio(LocalTime tiempoInicio) { this.tiempoInicio = tiempoInicio; }

    public LocalTime getTiempoFin() { return tiempoFin; }
    public void setTiempoFin(LocalTime tiempoFin) { this.tiempoFin = tiempoFin; }

    public LocalDate getDiaResolucion() { return diaResolucion; }
    public void setDiaResolucion(LocalDate diaResolucion) { this.diaResolucion = diaResolucion; }

    public Integer getMinutosResolucion() { return minutosResolucion; }
    public void setMinutosResolucion(Integer minutosResolucion) { this.minutosResolucion = minutosResolucion; }

    public LocalDateTime getFechaArchivado() { return fechaArchivado; }
    public void setFechaArchivado(LocalDateTime fechaArchivado) { this.fechaArchivado = fechaArchivado; }
}

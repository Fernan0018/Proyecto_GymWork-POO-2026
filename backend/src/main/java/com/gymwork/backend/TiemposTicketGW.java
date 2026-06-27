package com.gymwork.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Tiempos_TicketGW")
public class TiemposTicketGW {

    @Id
    @Column(name = "Id_Ticket")
    private Integer idTicket;

    @Column(name = "Tiempo_inicio")
    private LocalTime tiempoInicio;

    @Column(name = "Tiempo_fin")
    private LocalTime tiempoFin;

    @Column(name = "Dia_Resolucion")
    private LocalDate diaResolucion;

    @Column(name = "Minutos_Resolucion")
    private Integer minutosResolucion;

    public TiemposTicketGW() {}

    public Integer getIdTicket() { return idTicket; }
    public void setIdTicket(Integer idTicket) { this.idTicket = idTicket; }

    public LocalTime getTiempoInicio() { return tiempoInicio; }
    public void setTiempoInicio(LocalTime tiempoInicio) { this.tiempoInicio = tiempoInicio; }

    public LocalTime getTiempoFin() { return tiempoFin; }
    public void setTiempoFin(LocalTime tiempoFin) { this.tiempoFin = tiempoFin; }

    public LocalDate getDiaResolucion() { return diaResolucion; }
    public void setDiaResolucion(LocalDate diaResolucion) { this.diaResolucion = diaResolucion; }

    public Integer getMinutosResolucion() { return minutosResolucion; }
    public void setMinutosResolucion(Integer minutosResolucion) { this.minutosResolucion = minutosResolucion; }
}

package com.gymwork.backend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "Registro_TiempoPromGW")
public class RegistroTiempoPromGW {

    @Id
    @Column(name = "Fecha")
    private LocalDate fecha;

    @Column(name = "Promedio_minutos_resolucion")
    private Double promedioMinutosResolucion;

    public RegistroTiempoPromGW() {}

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Double getPromedioMinutosResolucion() { return promedioMinutosResolucion; }
    public void setPromedioMinutosResolucion(Double promedioMinutosResolucion) { this.promedioMinutosResolucion = promedioMinutosResolucion; }
}

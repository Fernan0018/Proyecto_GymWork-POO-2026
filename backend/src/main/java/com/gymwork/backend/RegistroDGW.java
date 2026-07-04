package com.gymwork.backend;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "table_registroDGW")
public class RegistroDGW {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Registro")
    private Long idRegistro;

    @Column(name = "Fecha")
    private LocalDate fecha;

    @Column(name = "Hora")
    private LocalTime hora;

    @Column(name = "Codigo_cliente")
    private String codigoCliente;

    @Column(name = "Idsede")
    private Integer idSede;

    @Column(name = "CodIns")
    private Integer codIns = 1;

    @Column(name = "Hora_inicio")
    private LocalTime horaIngreso;

    @Column(name = "Hora_final")
    private LocalTime horaSalida;

    public RegistroDGW() {}

    public Long getIdRegistro() { return idRegistro; }
    public void setIdRegistro(Long idRegistro) { this.idRegistro = idRegistro; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }

    public String getCodigoCliente() { return codigoCliente; }
    public void setCodigoCliente(String codigoCliente) { this.codigoCliente = codigoCliente; }

    public Integer getIdSede() { return idSede; }
    public void setIdSede(Integer idSede) { this.idSede = idSede; }

    public Integer getCodIns() { return codIns; }
    public void setCodIns(Integer codIns) { this.codIns = codIns; }

    public LocalTime getHoraIngreso() { return horaIngreso; }
    public void setHoraIngreso(LocalTime horaIngreso) { this.horaIngreso = horaIngreso; }

    public LocalTime getHoraSalida() { return horaSalida; }
    public void setHoraSalida(LocalTime horaSalida) { this.horaSalida = horaSalida; }
}

package com.gymwork.backend;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "MaquinasGW")
public class MaquinasGW {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Maquina")
    @JsonProperty("idMaquina")
    private Integer idMaquina;

    @Column(name = "Id_Sede")
    @JsonProperty("idSede")
    private Integer idSede;

    @Column(name = "Nombre_Modelo")
    @JsonProperty("nombreModelo")
    private String nombreModelo;

    @Column(name = "Ultimo_Mantenimiento")
    @JsonProperty("ultimoMantenimiento")
    private LocalDate ultimoMantenimiento;

    @Column(name = "Estado_Maquina")
    @JsonProperty("estadoMaquina")
    private String estadoMaquina;

    public MaquinasGW() {}

    public Integer getIdMaquina() { return idMaquina; }
    public void setIdMaquina(Integer idMaquina) { this.idMaquina = idMaquina; }

    public Integer getIdSede() { return idSede; }
    public void setIdSede(Integer idSede) { this.idSede = idSede; }

    public String getNombreModelo() { return nombreModelo; }
    public void setNombreModelo(String nombreModelo) { this.nombreModelo = nombreModelo; }

    public LocalDate getUltimoMantenimiento() { return ultimoMantenimiento; }
    public void setUltimoMantenimiento(LocalDate ultimoMantenimiento) { this.ultimoMantenimiento = ultimoMantenimiento; }

    public String getEstadoMaquina() { return estadoMaquina; }
    public void setEstadoMaquina(String estadoMaquina) { this.estadoMaquina = estadoMaquina; }
}

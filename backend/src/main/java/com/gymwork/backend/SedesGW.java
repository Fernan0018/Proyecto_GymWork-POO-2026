package com.gymwork.backend;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SedesGW")
public class SedesGW {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_sede")
    @JsonProperty("idSede")
    private Integer idSede;

    @Column(name = "Nombre_sede")
    @JsonProperty("nombreSede")
    private String nombreSede;

    @Column(name = "Departamento")
    @JsonProperty("departamento")
    private String departamento;

    @Column(name = "Distrito")
    @JsonProperty("distrito")
    private String distrito;

    @Column(name = "Estado")
    @JsonProperty("estado")
    private String estado;

    @Column(name = "Aforo")
    @JsonProperty("aforo")
    private Integer aforo;

    public SedesGW() {}

    public Integer getIdSede() { return idSede; }
    public void setIdSede(Integer idSede) { this.idSede = idSede; }

    public String getNombreSede() { return nombreSede; }
    public void setNombreSede(String nombreSede) { this.nombreSede = nombreSede; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Integer getAforo() { return aforo; }
    public void setAforo(Integer aforo) { this.aforo = aforo; }
}

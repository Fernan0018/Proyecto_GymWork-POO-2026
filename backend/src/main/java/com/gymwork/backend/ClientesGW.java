package com.gymwork.backend;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ClientesGW")
public class ClientesGW {

    @Id
    @Column(name = "Codigo_cliente")
    @JsonProperty("codigoCliente")
    private String codigoCliente;

    @Column(name = "Nombres_cliente")
    @JsonProperty("nombresCliente")
    private String nombresCliente;

    @Column(name = "Apellido_Paterno")
    @JsonProperty("apellidoPaterno")
    private String apellidoPaterno;

    @Column(name = "Apellido_Materno")
    @JsonProperty("apellidoMaterno")
    private String apellidoMaterno;

    @Column(name = "Fecha_de_Nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "Correo")
    private String correo;

    @Column(name = "Sexo")
    private String sexo;

    @Column(name = "Telefono")
    private String telefono;

    @Column(name = "Departamento")
    private String departamento;

    @Column(name = "Ciudad")
    private String ciudad;

    @Column(name = "Calle")
    private String calle;

    @Column(name = "Fecha_Registro")
    private LocalDate fechaRegistro;

    @Column(name = "TipoPlan")
    private String tipoPlan;

    @Column(name = "Id_Sede")
    private Integer idSede;

    public ClientesGW(){}
public String getCodigoCliente() {
    return codigoCliente;
}

public void setCodigoCliente(String codigoCliente) {
    this.codigoCliente = codigoCliente;
}

public String getNombresCliente() {
    return nombresCliente;
}

public void setNombresCliente(String nombresCliente) {
    this.nombresCliente = nombresCliente;
}

public String getApellidoPaterno() {
    return apellidoPaterno;
}

public void setApellidoPaterno(String apellidoPaterno) {
    this.apellidoPaterno = apellidoPaterno;
}

public String getApellidoMaterno() {
    return apellidoMaterno;
}

public void setApellidoMaterno(String apellidoMaterno) {
    this.apellidoMaterno = apellidoMaterno;
}

public LocalDate getFechaNacimiento() {
    return fechaNacimiento;
}

public void setFechaNacimiento(LocalDate fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
}

public String getCorreo() {
    return correo;
}

public void setCorreo(String correo) {
    this.correo = correo;
}

public String getSexo() {
    return sexo;
}

public void setSexo(String sexo) {
    this.sexo = sexo;
}

public String getTelefono() {
    return telefono;
}

public void setTelefono(String telefono) {
    this.telefono = telefono;
}

public String getDepartamento() {
    return departamento;
}

public void setDepartamento(String departamento) {
    this.departamento = departamento;
}

public String getCiudad() {
    return ciudad;
}

public void setCiudad(String ciudad) {
    this.ciudad = ciudad;
}

public String getCalle() {
    return calle;
}

public void setCalle(String calle) {
    this.calle = calle;
}

public LocalDate getFechaRegistro() {
    return fechaRegistro;
}

public void setFechaRegistro(LocalDate fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
}

public String getTipoPlan() {
    return tipoPlan;
}

public void setTipoPlan(String tipoPlan) {
    this.tipoPlan = tipoPlan;
}

public Integer getIdSede() {
    return idSede;
}

public void setIdSede(Integer idSede) {
    this.idSede = idSede;
}
   
}
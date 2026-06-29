package com.gymwork.backend;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "ClientesGW")
public class ClientesGW {
    
    @Id
    @Column(name = "Codigo_cliente")
    private String codigo;
    
    @Column(name = "Nombres_cliente")
    private String nombre;
    
    @Column(name = "Apellido_Paterno")
    private String apellidoPaterno;
    
    @Column(name = "Apellido_Materno")
    private String apellidoMaterno;
    
    @Transient
    private Integer edad;
    
    @Column(name = "Fecha_Registro")
    private Date fechaRegistro;
    
    @Column(name = "Sexo")
    private String sexo;
    
    @Column(name = "Correo")
    private String correo;
    
    @Column(name = "Telefono")
    private String telefono;
    
    @Column(name = "TipoPlan") 
    private String tipoPlan;
    
    @Column(name = "Departamento")
    private String departamento;
    
    @Column(name = "Ciudad")
    private String ciudad;
    
    @Column(name = "Calle")
    private String calle;
    
    @Column(name = "Fecha_de_Nacimiento") 
    private Date fechaNacimiento;
    
    @Column(name = "Id_Sede")
    private Integer idSede;

    // --- GETTERS Y SETTERS ---
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }
    
    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }
    
    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }
    
    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getTipoPlan() { return tipoPlan; }
    public void setTipoPlan(String tipoPlan) { this.tipoPlan = tipoPlan; }
    
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
    
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    
    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }
    
    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    
    public Integer getIdSede() { return idSede; }
    public void setIdSede(Integer idSede) { this.idSede = idSede; }
}
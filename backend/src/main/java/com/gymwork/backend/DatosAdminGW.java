package com.gymwork.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "Datos_AdminGW")
public class DatosAdminGW {

    @Id
    @Column(name = "DNI")
    @JsonProperty("dni")
    private String dni;

    @OneToOne
    @JoinColumn(name = "Id_Usuario", referencedColumnName = "Id_Usuario")
    private UsuariosGW usuario;

    @Column(name = "Nombres_Admin")
    @JsonProperty("nombre")
    private String nombre;

    @Column(name = "Apellidos_Admin")
    @JsonProperty("apellidos")
    private String apellidos;

    @Column(name = "FechaDeNacimiento")
    @JsonProperty("fechaNacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "Correo")
    @JsonProperty("correo")
    private String correo;

    @Column(name = "Telefono")
    @JsonProperty("telefono")
    private String telefono;

    public DatosAdminGW() {}

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public UsuariosGW getUsuario() { return usuario; }
    public void setUsuario(UsuariosGW usuario) { this.usuario = usuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}

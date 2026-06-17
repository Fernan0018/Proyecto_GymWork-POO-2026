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
@Table(name = "DatosAdmin")
public class DatosAdmin {

    @Id
    @Column(name = "ID_Datos")
    @JsonProperty("idDatos")
    private Integer idDatos;

    @OneToOne
    @JoinColumn(name = "ID_Admin", referencedColumnName = "ID_Admin")
    private Usuario usuario;

    @Column(name = "Nombre")
    @JsonProperty("nombre")
    private String nombre;

    @Column(name = "Apellidos")
    @JsonProperty("apellidos")
    private String apellidos;

    @Column(name = "fecha_nacimiento")
    @JsonProperty("fechaNacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "edad_actual", insertable = false, updatable = false)
    @JsonProperty("edadActual")
    private Integer edadActual;

    @Column(name = "correo_electronico")
    @JsonProperty("correo")
    private String correo;

    @Column(name = "numero_contacto")
    @JsonProperty("telefono")
    private String telefono;

    public DatosAdmin() {}

    public Integer getIdDatos() { return idDatos; }
    public void setIdDatos(Integer idDatos) { this.idDatos = idDatos; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public Integer getEdadActual() { return edadActual; }
    public void setEdadActual(Integer edadActual) { this.edadActual = edadActual; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}

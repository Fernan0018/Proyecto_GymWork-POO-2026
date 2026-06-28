package com.gymwork.backend;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "UBIGEO")
public class UbiGeo {

    @Id
    @Column(name = "C\u00F3digo")
    @JsonProperty("codigo")
    private String codigo;

    @Column(name = "Nombre")
    @JsonProperty("nombre")
    private String nombre;

    public UbiGeo() {}

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}

package com.gymwork.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "UsuariosGW")
public class UsuariosGW {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Usuario")
    @JsonProperty("idUsuario")
    private Integer idUsuario;

    @Column(name = "usuario")
    @JsonProperty("username")
    private String username;

    @Column(name = "Contraseña")
    @JsonProperty("password")
    private String password;

    @Column(name = "DeletePassword")
    @JsonProperty("deletePassword")
    private String deletePassword;

    public UsuariosGW() {}

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDeletePassword() { return deletePassword; }
    public void setDeletePassword(String deletePassword) { this.deletePassword = deletePassword; }
}

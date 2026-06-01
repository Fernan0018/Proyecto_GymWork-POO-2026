package com.gymwork.backend;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USUARIOS") // Obliga a Java a buscar en tu tabla real
public class Usuario {
    
    @Id
    @Column(name = "usuario") // Obliga a Java a buscar en la columna 'usuario'
    @JsonProperty("username") // Le dice que tu web lo envía como 'username'
    private String username;
    
    @Column(name = "password")
    @JsonProperty("password")
    private String password; 

    public Usuario() {}

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
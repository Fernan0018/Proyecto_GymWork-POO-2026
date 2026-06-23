package com.gymwork.backend;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SoporteGW")
public class SoporteGW {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Ticket")
    @JsonProperty("idTicket")
    private Integer idTicket;

    @Column(name = "Asunto_Ticket")
    @JsonProperty("asuntoTicket")
    private String asuntoTicket;

    @Column(name = "Remitente")
    @JsonProperty("remitente")
    private String remitente;

    @Column(name = "Categoria")
    @JsonProperty("categoria")
    private String categoria;

    @Column(name = "Id_Maquinas")
    @JsonProperty("idMaquinas")
    private Integer idMaquinas;

    @Column(name = "Id_Sede")
    @JsonProperty("idSede")
    private Integer idSede;

    @Column(name = "Prioridad")
    @JsonProperty("prioridad")
    private String prioridad;

    @Column(name = "Estado_Ticket")
    @JsonProperty("estadoTicket")
    private String estadoTicket;

    public SoporteGW() {}

    public Integer getIdTicket() { return idTicket; }
    public void setIdTicket(Integer idTicket) { this.idTicket = idTicket; }

    public String getAsuntoTicket() { return asuntoTicket; }
    public void setAsuntoTicket(String asuntoTicket) { this.asuntoTicket = asuntoTicket; }

    public String getRemitente() { return remitente; }
    public void setRemitente(String remitente) { this.remitente = remitente; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Integer getIdMaquinas() { return idMaquinas; }
    public void setIdMaquinas(Integer idMaquinas) { this.idMaquinas = idMaquinas; }

    public Integer getIdSede() { return idSede; }
    public void setIdSede(Integer idSede) { this.idSede = idSede; }

    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }

    public String getEstadoTicket() { return estadoTicket; }
    public void setEstadoTicket(String estadoTicket) { this.estadoTicket = estadoTicket; }
}

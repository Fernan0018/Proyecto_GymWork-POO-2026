package com.gymwork.backend;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PagosGW")
public class PagosGW {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Pago")
    private Long idPago;
    
    @Column(name = "Codigo_Cliente")
    private String codigoCliente;
    
    @Column(name = "tipoPlan")
    private String tipoPlan;
    
    @Column(name = "FechaPago")
    private LocalDateTime fechaPago = LocalDateTime.now();

    public PagosGW() {
    }

    public PagosGW(String codigoCliente, String tipoPlan) {
        this.codigoCliente = codigoCliente;
        this.tipoPlan = tipoPlan;
    }

    public Long getIdPago() { return idPago; }
    public void setIdPago(Long idPago) { this.idPago = idPago; }

    public String getCodigoCliente() { return codigoCliente; }
    public void setCodigoCliente(String codigoCliente) { this.codigoCliente = codigoCliente; }

    public String getTipoPlan() { return tipoPlan; }
    public void setTipoPlan(String tipoPlan) { this.tipoPlan = tipoPlan; }

    public LocalDateTime getFechaPago() { return fechaPago; }
    public void setFechaPago(LocalDateTime fechaPago) { this.fechaPago = fechaPago; }
}
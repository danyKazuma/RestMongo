package com.nttdata.semana2.model;

import java.math.BigDecimal;
import java.time.Instant;

public class TarjetaCreditoRestClient {
    public Integer tarjetaCredito;
    public Integer clienteId;
    public String numeroTarjeta;
    public String pin;
    public String fechaExpiracion;
    public String codigoValidacion;
    public Integer cuotaMensual;
    public Integer fechaPagoMensual;
    public BigDecimal saldo;
    public BigDecimal limiteCredito;
    public Boolean estado;
    public Instant fechaCreacion;
    public Instant fechaActualizacion;
}

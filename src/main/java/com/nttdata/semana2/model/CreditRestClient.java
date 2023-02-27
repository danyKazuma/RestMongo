package com.nttdata.semana2.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public class CreditRestClient {
    public Integer creditoId;
    public Integer clienteId;
    public LocalDate fechaInicio;
    public Integer pagoMensual;
    public BigDecimal saldoInicial;
    public BigDecimal saldoActual;
    public Boolean estado;
    public Instant fechaCreacion;
    public Instant fechaActualazion;
}

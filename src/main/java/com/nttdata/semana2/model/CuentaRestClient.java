package com.nttdata.semana2.model;

import java.math.BigDecimal;
import java.time.Instant;

public class CuentaRestClient {
    public Integer cuentaId;
    public Integer clienteId;
    public TarjetaDebitoRestClient tarjetaDebito;
    public BigDecimal monto;
    public Boolean cuentaPrincipal;
    public Boolean estado;
    public Instant fechaCreacion;
    public Instant fechaActualizacion;
}

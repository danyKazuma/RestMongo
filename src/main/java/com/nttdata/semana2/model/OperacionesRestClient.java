package com.nttdata.semana2.model;

import java.math.BigDecimal;
import java.time.Instant;

public class OperacionesRestClient {
    public Integer operationId;
    public Integer accountId;
    public Integer debitCardId;
    public Integer creditCardId;
    public Integer creditId;
    public String operationType;
    public BigDecimal amount;
    public Instant createdAt;
}

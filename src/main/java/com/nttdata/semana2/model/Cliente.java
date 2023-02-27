package com.nttdata.semana2.model;

import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.bson.types.ObjectId;
@MongoEntity(collection = "user")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public ObjectId clienteId;
    public String numeroTarjetaDebito;
    public String fechaTarjetaDebito;
    public String codigoValidacionDebito;
    public String tipoDocumento;
    public String numeroDocumento;
    public Integer clave;
    public String pin;
    public Boolean estado;
}

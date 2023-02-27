package com.nttdata.semana2.repository;

import com.nttdata.semana2.model.Cliente;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheMongoRepository<Cliente> {
}

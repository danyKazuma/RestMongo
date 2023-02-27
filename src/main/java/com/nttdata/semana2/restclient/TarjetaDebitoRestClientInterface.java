package com.nttdata.semana2.restclient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import com.nttdata.semana2.model.TarjetaDebitoRestClient;

import java.util.List;

@RegisterRestClient
@Path("/debit-cards")
public interface TarjetaDebitoRestClientInterface {

    @GET
    List<TarjetaDebitoRestClient> findAllDebitCards();
}



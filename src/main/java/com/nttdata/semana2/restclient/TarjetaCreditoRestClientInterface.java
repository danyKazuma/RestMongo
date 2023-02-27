package com.nttdata.semana2.restclient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import com.nttdata.semana2.model.TarjetaCreditoRestClient;

import java.util.List;

@RegisterRestClient
@Path("/credit-cards")
public interface TarjetaCreditoRestClientInterface {

    @GET
    List<TarjetaCreditoRestClient> findAllCreditCards();
}

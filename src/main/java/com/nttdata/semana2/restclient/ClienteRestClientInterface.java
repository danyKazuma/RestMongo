package com.nttdata.semana2.restclient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import com.nttdata.semana2.model.ClienteRestClient;

import java.util.List;

@RegisterRestClient
@Path("/clients")
public interface ClienteRestClientInterface {

    @GET
    List<ClienteRestClient> findAllClients();
}




package com.nttdata.semana2.restclient;

import com.nttdata.semana2.model.OperacionesRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


import java.util.List;

@RegisterRestClient
@Path("/operations")
public interface OperacionesRestClientInterface {

    @GET
    List<OperacionesRestClient> findAllOperations();
}

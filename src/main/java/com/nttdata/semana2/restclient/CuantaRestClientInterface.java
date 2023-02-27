package com.nttdata.semana2.restclient;

import com.nttdata.semana2.model.CuentaRestClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;


import java.util.List;

@RegisterRestClient
@Path("/accounts")
public interface CuantaRestClientInterface {

    @GET
    List<CuentaRestClient> findAllAccounts();
}



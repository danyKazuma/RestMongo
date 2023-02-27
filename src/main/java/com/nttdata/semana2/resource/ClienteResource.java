package com.nttdata.semana2.resource;

import com.nttdata.semana2.model.Cliente;
import com.nttdata.semana2.model.Login;
import com.nttdata.semana2.repository.ClienteRepository;
import com.nttdata.semana2.service.ClienteService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.bson.types.ObjectId;
import org.jboss.logging.Logger;

import java.net.URI;
import java.util.List;

@Path("/user")
public class ClienteResource {
    @Inject
    Logger logger;

    @Inject
    ClienteRepository clienteRepository;

    @Inject
    ClienteService clienteService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createUser(Cliente cliente) throws Exception {

        clienteService.createUser(cliente);

        return Response.created(new URI("/" + cliente.clienteId)).build();
    }

    @GET
    @Path("/findBydebitCard/{debitCardNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response findBydebitCardNumber(@PathParam("debitCardNumber") String debitCardNumber) {
        Cliente cliente = this.clienteService.findBydebitCardNumber(debitCardNumber);

        return cliente != null ? Response.ok(cliente).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/accesoApp")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response AccesoApp(Login login) {

        boolean status = this.clienteService.AccesoApp(login.numeroTarjetaDebito, login.clave);

        return Response.ok(status).status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<List<Cliente>> list() {
        return clienteService.listAllUsers();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUserById(@PathParam("id") String id) {
        Cliente cliente = clienteService.findUserById(id);
        return cliente != null ? Response.ok(cliente).build(): Response.status(Response.Status.NOT_FOUND).build();
    }
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateUser(@PathParam("id") String id, Cliente cliente) {
        logger.info("[RRM] userId: " + cliente.clienteId);
        cliente.clienteId = new ObjectId(id);
        logger.info("[RRM] userId: " + cliente.clienteId);
        clienteRepository.update(cliente);
        logger.info("[RRM] pasó");
        return Response.ok(cliente).build();
    }

 }

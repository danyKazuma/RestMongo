package com.nttdata.semana2.service;

import com.nttdata.semana2.model.Cliente;
import com.nttdata.semana2.model.TarjetaDebitoRestClient;
import com.nttdata.semana2.restclient.CuantaRestClientInterface;
import com.nttdata.semana2.restclient.ClienteRestClientInterface;
import com.nttdata.semana2.restclient.TarjetaDebitoRestClientInterface;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import com.nttdata.semana2.model.CuentaRestClient;
import java.util.List;
import java.util.stream.Collectors;
import com.nttdata.semana2.repository.ClienteRepository;
import com.nttdata.semana2.model.ClienteRestClient;
@ApplicationScoped
public class ClienteService implements HealthCheck {

    @Inject
    ClienteRepository clienteRepository;

    @RestClient
    ClienteRestClientInterface clientRestClient;

    @RestClient
    CuantaRestClientInterface accounttRestClient;

    @RestClient
    TarjetaDebitoRestClientInterface debitCardRestClient;

    @Inject 
    ReactiveMongoClient mongoClient;

    @Transactional
    public Cliente createUser(Cliente cliente) {
        if(findClient(clientRestClient.findAllClients(), cliente.numeroDocumento, cliente.tipoDocumento) == null){
            throw new NotFoundException("The identification document number " + cliente.numeroDocumento + " doesn't exists");
        }
        ClienteRestClient client = findClient(clientRestClient.findAllClients(), cliente.numeroDocumento,
                                                                                cliente.tipoDocumento);
            if(findAccount(accounttRestClient.findAllAccounts(), client.cuentaId) == null) {
            throw new NotFoundException("The client " + client.cuentaId + " doesn't have an associated account");
        }
        CuentaRestClient account = findAccount(accounttRestClient.findAllAccounts(), client.cuentaId);
            if(findDebitCard(debitCardRestClient.findAllDebitCards(), account.cuentaId, cliente) == null) {
            throw new NotFoundException("The debit card details aren't correct");
        }
        if (cliente.clienteId != null) {
            throw new NotFoundException("the user id " + cliente.clienteId + " already exist");
        }
        clienteRepository.persist(cliente);
        System.out.println("User created successfully.");
        return cliente;
    }

    public Uni<List<Cliente>> listAllUsers() {
        return getCollection().find()
                .map(doc -> {
                    Cliente cliente = new Cliente();
                    cliente.numeroTarjetaDebito = doc.getString("debitCardNumber");
                    cliente.fechaTarjetaDebito = doc.getString("debitCardDueDate");
                    cliente.codigoValidacionDebito = doc.getString("debitCardValidationCode");
                    cliente.tipoDocumento = doc.getString("identificationDocumentType");
                    cliente.numeroDocumento = doc.getString("identificationDocumentNumber");
                    return cliente;
                }).collect().asList();
    }

    public Cliente findUserById(String id) {
        ObjectId userId = new ObjectId(id);
        return clienteRepository.findById(userId);
    }

       
    @Transactional
    public Cliente updateUser(String id, Cliente cliente) {
        cliente.clienteId = new ObjectId(id);
        if (clienteRepository.findById((new ObjectId(id))) != null) {
            cliente.clienteId = new ObjectId(id);
            clienteRepository.update(cliente);
            return cliente;
        }
        throw new NotFoundException("the user id " + cliente.clienteId + " doesn't exist");
    }

    @Transactional
    public Cliente deleteUser(String id, Cliente cliente) {
        cliente.clienteId = new ObjectId(id);
        if (clienteRepository.findById((new ObjectId(id))) != null) {
            cliente.clienteId = new ObjectId(id);
            cliente.estado.equals(false);
            clienteRepository.update(cliente);
            return cliente;
        }
        throw new NotFoundException("the user id " + cliente.clienteId + " doesn't exist");
    }

    public ClienteRestClient findClient(List<ClienteRestClient> clientList, String documentIdentity, String documentIdentityType) {

        ClienteRestClient client = clientList.stream().filter(x -> x.numeroDocumento.equalsIgnoreCase(documentIdentity)).collect(Collectors.toList()).get(0);

        if(client != null) {
            if(client.tipoDocumento.equalsIgnoreCase(documentIdentityType)){
                return client;
            }
        }

        return null;
    }

    public CuentaRestClient findAccount(List<CuentaRestClient> accountList, Integer clientId) {

        CuentaRestClient account = accountList.stream().filter(x -> x.clienteId == clientId && x.cuentaPrincipal.equals(true)).collect(Collectors.toList()).get(0);

        if(account != null) {
            return account;
        }
        return null;
    }

    public TarjetaDebitoRestClient findDebitCard(List<TarjetaDebitoRestClient> debitCardList, Integer accountId, Cliente cliente) {

        TarjetaDebitoRestClient debitCard = debitCardList.stream().filter(x -> x.accountId == accountId && x.cardNumber.equalsIgnoreCase(cliente.numeroTarjetaDebito)
                                        && x.expirationDate.equalsIgnoreCase(cliente.fechaTarjetaDebito) && x.cardValidationCode.equalsIgnoreCase(cliente.codigoValidacionDebito)).collect(Collectors.toList()).get(0);

        if(debitCard != null) {

            return debitCard;
        }

        return null;
    }

    public Cliente findBydebitCardNumber(String debitCardNumber) {
        Cliente cliente = this.clienteRepository.find("debitCardNumber", debitCardNumber).firstResult();
        if (cliente == null) {
            throw new NotFoundException("USUARIO NO REGISTRADO");
        }
        return cliente;
    }

    public Boolean AccesoApp(String debitCardNumber, Integer password) {
        boolean status = false;
        Cliente cliente = this.findBydebitCardNumber(debitCardNumber);
        if (password.toString().equals(cliente.clave.toString())) {
            status = true;
            this.call();
        }
        return status;
    }

    @Override
    public HealthCheckResponse call() {

        return HealthCheckResponse.up("Service Ok");
    }
    private ReactiveMongoCollection<Document> getCollection(){
        return mongoClient.getDatabase("multichannel_system").getCollection("user");
    }
}

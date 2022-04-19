package com.example.bankroute.Controller;

import com.example.bankroute.Dto.ClientResponse;
import com.example.bankroute.Model.Client;
import com.example.bankroute.Dto.ClientException;
import com.example.bankroute.Service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService service;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public List<Client> getAllClients() {
        return service.getAllClients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClientInfo(@PathVariable String id) {
        try {
            Client client = service.getClientInfo(id);
            return new ResponseEntity<>(new ClientResponse(client, null), HttpStatus.OK);
        } catch (ClientException ce) {
            return new ResponseEntity<>(new ClientResponse(null, ce.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> updateClientInfo(@PathVariable String id, @RequestBody Client body) {
        try {
            service.updateClientInfo(id, body);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ClientException ce) {
            return new ResponseEntity<>(new ClientResponse(null, ce.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ClientResponse> addClientProspect(@RequestBody Client client) {
        try {
            service.addClientProspect(client);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            if (ex instanceof ClientException)
                return new ResponseEntity<>(new ClientResponse(null, ex.getMessage()), HttpStatus.CONFLICT);
            else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<ClientResponse> deleteClients() {
        service.deleteClients();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{idclient}")
    public ResponseEntity<ClientResponse> deleteClient(@PathVariable String idclient) {
        try {
            service.deleteClient(idclient);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ClientException ce) {
            return new ResponseEntity<>(new ClientResponse(null, ce.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
package com.example.bankroute.Service;

import com.example.bankroute.Controller.AccountRepository;
import com.example.bankroute.Controller.ClientRepository;
import com.example.bankroute.Controller.HistoryRepository;
import com.example.bankroute.Dto.ClientException;
import com.example.bankroute.Model.Account;
import com.example.bankroute.Model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private HistoryRepository historyRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientInfo(String id) throws ClientException {
        if (clientRepository.findAllById(id) == null) {
            throw new ClientException("Client does not exist !");
        } else {
            return clientRepository.findAllById(id);
        }
    }

    public void updateClientInfo(String id, Client body) throws ClientException {
        Client client = clientRepository.findAllById(id);
        if (client == null) {
            throw new ClientException("Client cannot be updated !");
        } else {
            client = body;
            clientRepository.save(client);
        }
    }


    public void addClientProspect(Client client) throws ClientException {
        for (int i = 0; i < clientRepository.findAll().size(); i++) {
            if (clientRepository.findAll().get(i).getNname().equals(client.getNname()) &&
                    clientRepository.findAll().get(i).getLname().equals(client.getLname()) &&
                    clientRepository.findAll().get(i).getDob().equals(client.getDob())) {
                throw new ClientException("Client incorrect or already added !");
            } else {
                break;
            }
        }
        client.setStatus("Prospect");
        clientRepository.insert(client);
    }

    public void deleteClients() {
        clientRepository.deleteAll();
        accountRepository.deleteAll();
    }

    public void deleteClient(String idclient) throws ClientException {
        Client client = clientRepository.findAllById(idclient);
        if (client == null) {
            throw new ClientException("Client does not exist !");
        } else {
            List<Account> accounts = accountRepository.findAll();
            if (client.getStatus() == "Validated") {
                for (Account account : accounts) {
                    if (account.getIdclient().equals(idclient)) {
                        accountRepository.delete(account);
                    }
                }
            }
            clientRepository.deleteById(idclient);
        }
    }
}
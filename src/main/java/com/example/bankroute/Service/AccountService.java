package com.example.bankroute.Service;

import com.example.bankroute.Controller.AccountRepository;
import com.example.bankroute.Controller.ClientRepository;
import com.example.bankroute.Controller.HistoryRepository;
import com.example.bankroute.Dto.AccountsException;
import com.example.bankroute.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private ClientRepository clientRepository;

    public List<Account> getAccounts(String idclient) throws AccountsException {
        if (clientRepository.findAllById(idclient) == null) {
            throw new AccountsException("Accounts do not exist !");
        } else {
            List<Account> accounts = accountRepository.findAll();
            List<Account> list = new LinkedList<>();
            for (Account account : accounts) {
                if (account.getIdclient().equals(idclient)) {
                    list.add(account);
                }
            }
            return list;
        }
    }

    public List<Account> getAccount(String idclient, String iban) throws AccountsException {
        List<Account> accounts = accountRepository.findAll();
        for (Account account : accounts) {
            if (account.getIdclient().equals(idclient) && account.getIban().equals(iban)) {
                return Collections.singletonList(account);
            } else if (clientRepository.findAllById(idclient) == null) {
                break;
            }
        }
        throw new AccountsException("Account does not exist !");
    }

    public void addClient(String idclient, AccountType accType) throws AccountsException {
        Client client = clientRepository.findAllById(idclient);
        if (client == null) {
            throw new AccountsException("Client does not exist !");
        } else {
            client.setStatus("Validated");
            List<Account> list = new LinkedList<>();
            for (int i = 0; i < accType.getAccType().size(); i++) {
                Account account = new Account(idclient, Integer.toString(i), accType.getAccType().get(i).toString(), 0);
                list.add(account);
            }
            clientRepository.save(client);
            accountRepository.insert(list);
        }
    }

    public void credit(String idclient, String iban, Amount amount) throws AccountsException {
        Client client = clientRepository.findAllById(idclient);
        List<Account> accounts = accountRepository.findAll();
        for (Account accountt : accounts) {
            if (accountt.getIdclient().equals(idclient) && accountt.getIban().equals(iban) && client.getStatus().equals("Validated")) {
                List<Account> accountss = accountRepository.findAll();
                for (Account account : accountss) {
                    if (account.getIdclient().equals(idclient) && account.getIban().equals(iban)) {
                        account.setBalance(account.getBalance() + amount.getAmount());
                        History history = new History(idclient, iban, account.getAccType(), amount.getAmount(), LocalDateTime.now().toString());
                        accountRepository.save(account);
                        historyRepository.insert(history);
                    }
                }
            } else {
                throw new AccountsException("Client or account does not exit !");
            }
        }
    }
}
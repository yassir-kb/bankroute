package com.example.bankroute.Controller;

import com.example.bankroute.Dto.AccountsException;
import com.example.bankroute.Dto.AccountsResponse;
import com.example.bankroute.Model.Account;
import com.example.bankroute.Model.AccountType;
import com.example.bankroute.Model.Amount;
import com.example.bankroute.Model.Loan;
import com.example.bankroute.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/client")
public class AccountController {
    @Autowired
    private AccountService service;

    @GetMapping("/{idclient}/account")
    public ResponseEntity<AccountsResponse> getAccounts(@PathVariable String idclient) {
        try {
            List<Account> accounts = service.getAccounts(idclient);
            return new ResponseEntity<>(new AccountsResponse(accounts, null), HttpStatus.OK);
        } catch (AccountsException ae) {
            return new ResponseEntity<>(new AccountsResponse(null, ae.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{idclient}/account/{iban}")
    public ResponseEntity<AccountsResponse> getAccount(@PathVariable String idclient, @PathVariable String iban) {
        try {
            List<Account> account = service.getAccount(idclient, iban);
            return new ResponseEntity<>(new AccountsResponse(account, null), HttpStatus.OK);
        } catch (AccountsException ae) {
            return new ResponseEntity<>(new AccountsResponse(null, ae.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/account")
    public ResponseEntity<AccountsResponse> addClient(@PathVariable String id, @RequestBody AccountType accType) {
        try {
            service.addClient(id, accType);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            if (ex instanceof AccountsException)
                return new ResponseEntity<>(new AccountsResponse(null, ex.getMessage()), HttpStatus.CONFLICT);
            else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{idclient}/account/{iban}/{amount}")
    public ResponseEntity<AccountsResponse> credit(@PathVariable String idclient, @PathVariable String iban, @PathVariable float amount) {
        try {
            Amount amount1 = new Amount(amount);
            service.credit(idclient, iban, amount1);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (AccountsException ae) {
            return new ResponseEntity<>(new AccountsResponse(null, ae.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{idclient}/account/{iban}")
    public ResponseEntity<AccountsResponse> loan(@PathVariable String idclient, @PathVariable String iban, @RequestBody Loan loan) {
        try {
            service.loan(idclient, iban, loan);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (AccountsException ae) {
            return new ResponseEntity<>(new AccountsResponse(null, ae.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}

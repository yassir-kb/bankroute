package com.example.bankroute.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Account {
    private String id;
    private String idclient;
    private String iban;
    private String accType;
    private float balance;

    public Account(String idclient, String iban, String accType, float balance) {
        this.idclient = idclient;
        this.iban = iban;
        this.accType = accType;
        this.balance = balance;
    }
}
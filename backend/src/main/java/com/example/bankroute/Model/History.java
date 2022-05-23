package com.example.bankroute.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class History {
    private String id;
    private String idclient;
    private String iban;
    private String accType;
    private float amount;
    private String located;

    public History(String idclient, String iban, String accType, float amount, String located) {
        this.idclient = idclient;
        this.iban = iban;
        this.accType = accType;
        this.amount = amount;
        this.located = located;
    }
}
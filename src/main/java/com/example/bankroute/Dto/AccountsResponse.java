package com.example.bankroute.Dto;

import com.example.bankroute.Model.Account;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountsResponse {

    private List<Account> accounts;
    private String bankException;

    public AccountsResponse(List<Account> accounts, String bankException) {
        this.accounts = accounts;
        this.bankException = bankException;
    }
}
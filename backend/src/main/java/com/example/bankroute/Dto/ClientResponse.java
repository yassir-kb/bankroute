package com.example.bankroute.Dto;

import com.example.bankroute.Model.Client;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientResponse {

    private Client client;
    private String portalException;

    public ClientResponse(Client client, String portalException) {
        this.client = client;
        this.portalException = portalException;
    }
}
package com.example.bankroute.Controller;

import com.example.bankroute.Model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientRepository extends MongoRepository<Client, String> {
    Client findAllById(String id);
}
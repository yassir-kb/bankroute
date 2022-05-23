package com.example.bankroute.Controller;

import com.example.bankroute.Model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
}
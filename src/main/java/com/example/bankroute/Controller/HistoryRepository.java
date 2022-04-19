package com.example.bankroute.Controller;

import com.example.bankroute.Model.History;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HistoryRepository extends MongoRepository<History, String> {
}
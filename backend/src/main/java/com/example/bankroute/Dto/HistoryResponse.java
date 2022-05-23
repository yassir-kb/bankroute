package com.example.bankroute.Dto;

import com.example.bankroute.Model.History;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoryResponse {

    private List<History> histories;
    private String bankException;

    public HistoryResponse(List<History> histories, String bankException) {
        this.histories = histories;
        this.bankException = bankException;
    }
}
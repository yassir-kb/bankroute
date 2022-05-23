package com.example.bankroute.Controller;

import com.example.bankroute.Dto.HistoryException;
import com.example.bankroute.Dto.HistoryResponse;
import com.example.bankroute.Model.History;
import com.example.bankroute.Service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/client")
public class HistoryController {
    @Autowired
    private HistoryService service;


    @GetMapping("/{idclient}/account/history")
    public ResponseEntity<HistoryResponse> getHistories(@PathVariable String idclient) {
        try {
            List<History> histories = service.getHistories(idclient);
            return new ResponseEntity<>(new HistoryResponse(histories, null), HttpStatus.OK);
        } catch (HistoryException he) {
            return new ResponseEntity<>(new HistoryResponse(null, he.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{idclient}/account/{iban}/history")
    public ResponseEntity<HistoryResponse> getHistory(@PathVariable String idclient, @PathVariable String iban) {
        try {
            List<History> history = service.getHistory(idclient, iban);
            return new ResponseEntity<>(new HistoryResponse(history, null), HttpStatus.OK);
        } catch (HistoryException he) {
            return new ResponseEntity<>(new HistoryResponse(null, he.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{idclient}/account/{iban}/history/{historyid}")
    public ResponseEntity<HistoryResponse> getOperation(@PathVariable String idclient, @PathVariable String iban, @PathVariable String historyid) {
        try {
            List<History> histories = service.getOperation(idclient, iban, historyid);
            return new ResponseEntity<>(new HistoryResponse(histories, null), HttpStatus.OK);
        } catch (HistoryException he) {
            return new ResponseEntity<>(new HistoryResponse(null, he.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}

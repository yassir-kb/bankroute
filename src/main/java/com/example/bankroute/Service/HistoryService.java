package com.example.bankroute.Service;

import com.example.bankroute.Controller.ClientRepository;
import com.example.bankroute.Controller.HistoryRepository;
import com.example.bankroute.Dto.HistoryException;
import com.example.bankroute.Model.History;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private ClientRepository clientRepository;

    public List<History> getHistories(String idclient) throws HistoryException {
        if (clientRepository.findAllById(idclient) == null) {
            throw new HistoryException("History do not exit !");
        } else {
            List<History> histories = historyRepository.findAll();
            List<History> list = new LinkedList<>();
            for (History history : histories) {
                if (history.getIdclient().equals(idclient)) {
                    list.add(history);
                }
            }
            return list;
        }
    }

    public List<History> getHistory(String idclient, String iban) throws HistoryException {
        List<History> histories = historyRepository.findAll();
        List<History> list = new LinkedList<>();
        for (History history : histories) {
            if (history.getIdclient().equals(idclient) && history.getIban().equals(iban)) {
                list.add(history);
                return list;
            } else {
                break;
            }
        }
        throw new HistoryException("Operations does not exit !");
    }

    public List<History> getOperation(String idclient, String iban, String id) throws HistoryException {
        List<History> histories = historyRepository.findAll();
        List<History> list = new LinkedList<>();
        for (History history : histories) {
            if (history.getIdclient().equals(idclient) && history.getIban().equals(iban) && history.getId().equals(id)) {
                list.add(history);
                return list;
            } else {
                break;
            }
        }
        throw new HistoryException("Operation does not exit !");
    }
}
package com.cg.api;

import com.cg.model.Transfer;
import com.cg.model.dto.history.HistoryTransferResDTO;
import com.cg.service.transfer.ITransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryTransferAPI {

    @Autowired
    private ITransferService transferService;

    @GetMapping("/transfer")
    public ResponseEntity<?> showHistoryTransfer(){

        List<Transfer> transfers = transferService.findAll();

        List<HistoryTransferResDTO> historyTransferResDTOS = new ArrayList<>();
        for (Transfer transfer: transfers) {

            historyTransferResDTOS.add(transfer.toHistoryTransferResDTO());
        }

        return new ResponseEntity<>(historyTransferResDTOS,HttpStatus.OK);
    }
}

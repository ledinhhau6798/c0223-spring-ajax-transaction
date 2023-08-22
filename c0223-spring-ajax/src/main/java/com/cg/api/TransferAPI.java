package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.model.Customer;
import com.cg.model.dto.transfer.TransferCreReqDTO;
import com.cg.model.dto.transfer.TransferCreResDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/transfers")
public class TransferAPI {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private AppUtils appUtils;

    @PostMapping
    public ResponseEntity<?> transfer(@RequestBody TransferCreReqDTO transferCreReqDTO, BindingResult bindingResult) {
        new TransferCreReqDTO().validate(transferCreReqDTO, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Long senderId = transferCreReqDTO.getSenderId();
        Long recipientId = transferCreReqDTO.getRecipientId();

        Customer sender = customerService.findById(senderId).get();

        BigDecimal totalSender = transferCreReqDTO.getTransferAmount().add(transferCreReqDTO.getTransferAmount().multiply(BigDecimal.valueOf(0.1)));

        if (sender.getBalance().compareTo(totalSender) < 0) {
            throw new DataInputException("tài khoản của bạn không đủ để thực hiện");
        }


        customerService.transfer(transferCreReqDTO);
        Customer senderNew = customerService.findById(senderId).get();
        Customer recipientNew = customerService.findById(recipientId).get();

        TransferCreResDTO transferCreResDTO = new TransferCreResDTO();


        transferCreResDTO.setSender(senderNew.toCustomerResDTO());
        transferCreResDTO.setRecipient(recipientNew.toCustomerResDTO());

        return new ResponseEntity<>(transferCreResDTO, HttpStatus.OK);
    }
}

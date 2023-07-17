package com.cg.model.dto.transfer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class TransferCreReqDTO implements Validator {
    private Long senderId;
    private Long recipientId;
    private BigDecimal transferAmount;

    @Override
    public boolean supports(Class<?> clazz) {
        return TransferCreReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TransferCreReqDTO transferCreReqDTO = (TransferCreReqDTO) target;

        if (transferCreReqDTO.getRecipientId() == transferCreReqDTO.getSenderId()){
            errors.rejectValue("transferAmount","transfer.transferAmount.duplicate","nguoi chuyen trung nguoi nhan");
        }

        BigDecimal transferAmount = transferCreReqDTO.getTransferAmount();
        if (transferAmount == null){
            errors.rejectValue("transferAmount","transfer.transferAmount.null","so tien khong duoc null");
            return;
        }

        BigDecimal transactionAmount = transferCreReqDTO.getTransferAmount();
        if (transactionAmount.compareTo(BigDecimal.ZERO) <= 0) {
            errors.rejectValue("transferAmount","transfer.transferAmount.zero","so tien phai lon hon 0");
        }


    }
}

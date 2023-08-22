package com.cg.model.dto.transfer;

import com.cg.model.Customer;
import com.cg.service.customer.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.Optional;

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
            errors.rejectValue("transferAmount","transfer.transferAmount.duplicate","Người chuyển không được trùng người nhận");
        }

        BigDecimal transferAmount = transferCreReqDTO.getTransferAmount();
        if (transferAmount == null){
            errors.rejectValue("transferAmount","transfer.transferAmount.null","số tiền không được null");
            return;
        }


        if (transferAmount.compareTo(BigDecimal.ZERO) <= 0) {
            errors.rejectValue("transferAmount","transfer.transferAmount.zero","số tiền phải lớn hơn 0");
        }



    }
}

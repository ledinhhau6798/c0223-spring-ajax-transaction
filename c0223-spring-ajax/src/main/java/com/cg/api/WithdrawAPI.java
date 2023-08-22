package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.model.Customer;
import com.cg.model.Withdraw;
import com.cg.model.dto.customer.CustomerResDTO;
import com.cg.model.dto.withdraw.WithdrawCreReqDTO;
import com.cg.service.customer.ICustomerService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/withdraws")
public class WithdrawAPI {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ValidateUtils validateUtils;

    @PostMapping("/{customerId}")
    public ResponseEntity<?> withdraw(@PathVariable("customerId") String customerIdStr, @RequestBody WithdrawCreReqDTO withdrawCreReqDTO, BindingResult bindingResult){
        new WithdrawCreReqDTO().validate(withdrawCreReqDTO, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        if (!validateUtils.isNumberValid(customerIdStr)) {
            throw new RuntimeException("Mã khách hàng không hợp lệ");
        }

        Long customerId = Long.parseLong(customerIdStr);

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (customerOptional.isEmpty()){
             throw new RuntimeException("Mã khách hàng không tồn tại");
        }

        Customer customer = customerOptional.get();

        BigDecimal transactionAmount = BigDecimal.valueOf(Long.parseLong(withdrawCreReqDTO.getTransactionAmount()));

        if (customer.getBalance().compareTo(transactionAmount) < 0){
            throw new DataInputException("số tiền rút vượt quá tiền tài khoản");
        }

        Withdraw withdraw = new Withdraw();

        withdraw.setTransactionAmount(transactionAmount);
        withdraw.setCustomer(customer);

        CustomerResDTO newCustomer = customerService.withdraw(withdraw).toCustomerResDTO();


        return new ResponseEntity<>(newCustomer, HttpStatus.OK);
    }
}

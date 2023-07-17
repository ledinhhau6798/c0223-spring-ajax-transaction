package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.model.Customer;
import com.cg.model.dto.customer.*;
import com.cg.service.customer.ICustomerService;
import com.cg.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {
    @Autowired
    private ICustomerService customerService;

     @Autowired
    private ValidateUtils validateUtils;

    @GetMapping
    public ResponseEntity<?> getAllCustomer(){
        List<CustomerResDTO> customerResDTOS = customerService.findAllCustomerResDTO();

        return new ResponseEntity<>(customerResDTOS, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getById(@PathVariable Long customerId){
        Customer customer = customerService.findById(customerId).orElseThrow(() -> {
            throw new DataInputException("Mã khách hàng không tồn tại");
        });

        CustomerResDTO customerResDTO = customer.toCustomerResDTO();
        return new ResponseEntity<>(customerResDTO,HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CustomerCreReqDTO customerCreReqDTO){

        String email = customerCreReqDTO.getEmail().trim();
        Boolean emailExits = customerService.existsByEmail(email);

        if (emailExits){
            throw new EmailExistsException("Email đã tồn tại");
        }

        CustomerCreResDTO customerCreResDTO = customerService.create(customerCreReqDTO);
        return new ResponseEntity<>(customerCreResDTO,HttpStatus.CREATED);
    }

    @GetMapping("/recipients-without-sender/{senderId}")
    public ResponseEntity<?> getAllRecipientsWithoutSender(@PathVariable Long senderId) {

        List<CustomerResDTO> recipients = customerService.findAllRecipientsWithoutSenderId(senderId);

        return new ResponseEntity<>(recipients, HttpStatus.OK);
    }

    @PostMapping("/delete/{customerId}")
    public ResponseEntity<?> delete(@PathVariable String customerId){
         if (!validateUtils.isNumberValid(customerId)) {
            throw new DataInputException("Mã khách hàng không hợp lệ");
        }

        customerService.deleteByIdTrue(Long.valueOf(customerId));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/edit/{customerId}")
    public ResponseEntity<?> update(@PathVariable("customerId")String customerIdStr,@RequestBody CustomerUpReqDTO customerUpReqDTO){
        if (!validateUtils.isNumberValid(customerIdStr)) {
            Map<String, String> data = new HashMap<>();
            data.put("message", "Mã khách hàng không hợp lệ");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        Long customerId = Long.parseLong(customerIdStr);
        String email = customerUpReqDTO.getEmail().trim();
        Boolean emailExits = customerService.existsByEmailAndIdNot(email,customerId);

        if (emailExits){
            throw new EmailExistsException("Email đã tồn tại");
        }

       Customer customer = customerService.findById(customerId).orElseThrow(() -> {
           throw new DataInputException("Mã khách hàng không tồn tại");
        });

        Customer customerUpdate = customerService.update(customer, customerUpReqDTO);

        CustomerUpResDTO customerUpResDTO = customerUpdate.toCustomerUpResDTO();


        return new ResponseEntity<>(customerUpResDTO,HttpStatus.OK);

    }
}

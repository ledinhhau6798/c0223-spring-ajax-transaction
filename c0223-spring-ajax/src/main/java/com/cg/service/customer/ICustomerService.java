package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Withdraw;
import com.cg.model.dto.customer.*;
import com.cg.model.dto.transfer.TransferCreReqDTO;
import com.cg.service.IGeneralService;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ICustomerService extends IGeneralService<Customer,Long> {
     List<CustomerResDTO> findAllCustomerResDTO();
     Boolean existsByEmail(String email);

     Boolean existsByEmailAndIdNot(String email, Long id);

     CustomerCreResDTO create(CustomerCreReqDTO customerCreReqDTO);

     Customer update(Customer customer, CustomerUpReqDTO customerUpReqDTO);

      Customer deposit(Deposit deposit);

      Customer withdraw(Withdraw withdraw);

      void transfer(TransferCreReqDTO transferCreReqDTO);
      void deleteByIdTrue(Long id);

      List<CustomerResDTO> findAllRecipientsWithoutSenderId(@Param("senderId") Long senderId);

      Optional<CustomerResDTO> findCustomerDTOById(Long id);


}

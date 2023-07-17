package com.cg.model.dto.customer;

import com.cg.model.Customer;
import com.cg.model.LocationRegion;
import com.cg.model.dto.locationRegion.LocationRegionUpReqDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CustomerUpReqDTO {
    private String fullName;
    private String email;
    private String phone;

    private LocationRegionUpReqDTO locationRegion;

    public Customer toCustomer(Long customerId, LocationRegion locationRegion) {
        return new Customer()
                .setId(customerId)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setLocationRegion(locationRegion)
                ;
    }
}

package com.cg.model;



import com.cg.model.dto.customer.CustomerCreResDTO;
import com.cg.model.dto.customer.CustomerResDTO;
import com.cg.model.dto.customer.CustomerUpResDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
@Accessors(chain = true)
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(precision = 10, columnDefinition = "decimal default 0", updatable = false)
    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "location_region_id",referencedColumnName = "id",nullable = false)
    private LocationRegion locationRegion;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Deposit> deposits;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Withdraw> withdraws;

    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private List<Transfer> senders;

    @OneToMany(mappedBy = "recipient")
    @JsonIgnore
    private List<Transfer> recipients;

    public CustomerResDTO toCustomerResDTO(){
        return new CustomerResDTO()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setBalance(balance)
                .setLocationRegion(locationRegion.toLocationRegionDTO())
                ;
    }

    public CustomerCreResDTO toCustomerCreResDTO() {
        return new CustomerCreResDTO()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setBalance(balance)
                .setLocationRegion(locationRegion.toLocationRegionCreResDTO())
                ;
    }

    public CustomerUpResDTO toCustomerUpResDTO() {
        return new CustomerUpResDTO()
                .setId(id)
                .setFullName(fullName)
                .setEmail(email)
                .setPhone(phone)
                .setBalance(balance)
                .setLocationRegion(locationRegion.toLocationRegionUpResDTO())
                ;
    }
}

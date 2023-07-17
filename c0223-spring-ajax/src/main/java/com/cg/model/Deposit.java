package com.cg.model;


import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "deposits")
@Accessors(chain = true)
public class Deposit extends BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;


    @Column(name = "transaction_amount", precision = 10, scale = 0, nullable = false)
    private BigDecimal transactionAmount;


    @Override
    public String toString() {
        return "Deposit{" +
                "id=" + id +
                ", transactionAmount=" + transactionAmount +
                '}';
    }



}

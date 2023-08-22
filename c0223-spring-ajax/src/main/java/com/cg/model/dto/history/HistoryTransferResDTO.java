package com.cg.model.dto.history;

import com.cg.model.dto.customer.CustomerResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class HistoryTransferResDTO {
    private Long id;
    private String sender;
    private String emailSender;
    private BigDecimal transferAmount;
    private BigDecimal transactionAmount;
    private String recipient;
    private String emailRecipient;
}

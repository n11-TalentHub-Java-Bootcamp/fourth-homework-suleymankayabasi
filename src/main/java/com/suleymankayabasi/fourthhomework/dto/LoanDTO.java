package com.suleymankayabasi.fourthhomework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanDTO {

    private Long loanId;
    private BigDecimal principalDebt;
    private BigDecimal arrears;
    private Date dueDate;
    private String userId; //User.userId mappingde buna dikkat
    private BigDecimal lateFeeAmount;
}

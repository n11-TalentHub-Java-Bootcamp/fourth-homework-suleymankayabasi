package com.suleymankayabasi.fourthhomework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollectionDTO {

    private Long collectionId;
    private LocalDate registrationDate;
    private BigDecimal principalDebtAmount;
    private BigDecimal loanAmount;
    private String colectionLoanType;
    private Long loanId;
    private Long loanUserId;
    private BigDecimal lateFeeAmount ;
}

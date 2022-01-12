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
public class CollectionDTO {

    private Long collectionId;
    private Date registrationDate;
    private BigDecimal principalDebtAmount;
    private BigDecimal loanAmount;
    private String loanType;
    private Long loanId;
    private Long loanUserId;

}

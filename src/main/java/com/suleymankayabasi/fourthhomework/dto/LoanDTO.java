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
public class LoanDTO {

    private Long loanId;
    private BigDecimal principalDebt;
    private BigDecimal arrears = principalDebt;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private String loanType;
    private Long userId;

}

package com.suleymankayabasi.fourthhomework.service;

import com.suleymankayabasi.fourthhomework.dto.LoanDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ILoanService {

    LoanDTO save(LoanDTO loanDTO);
    List<LoanDTO> listLoans(LocalDate firstDate, LocalDate lastDate);
    List<LoanDTO> listAllLoansByUserId(Long id);
    List<LoanDTO> listAllDueDateLoansByUserId(Long id);
    BigDecimal returnTotalDebtAmount(Long id);
    BigDecimal returnDueDateDebtAmount(Long id);
    BigDecimal returnSnapLateFeeAmount(Long id);
}

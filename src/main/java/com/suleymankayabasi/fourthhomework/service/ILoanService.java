package com.suleymankayabasi.fourthhomework.service;

import com.suleymankayabasi.fourthhomework.dto.LoanDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ILoanService {

    LoanDTO save(LoanDTO loanDTO);
    List<LoanDTO> listLoans(Date firstDate,Date lastDate);
    List<LoanDTO> listAllLoansByUserId(Long id);
    List<LoanDTO> listAllDueDateLoansByUserId(Long id);
    BigDecimal returnTotalDebtAmount(Long id);
    BigDecimal returnDueDateDebtAmount(Long id);
    BigDecimal returnSnapLateFeeAmount(Long id);
}

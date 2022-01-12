package com.suleymankayabasi.fourthhomework.service;

import com.suleymankayabasi.fourthhomework.dto.LoanDTO;
import com.suleymankayabasi.fourthhomework.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class LoanService implements ILoanService{

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public LoanDTO save(LoanDTO loanDTO) {
        return null;
    }

    @Override
    public List<LoanDTO> listLoans(Date firstDate, Date lastDate) {
        return null;
    }

    @Override
    public List<LoanDTO> listAllLoansByUserId(Long id) {
        return null;
    }

    @Override
    public List<LoanDTO> listAllDueDateLoansByUserId(Long id) {
        return null;
    }

    @Override
    public BigDecimal returnTotalDebtAmount(Long id) {
        return null;
    }

    @Override
    public BigDecimal returnDueDateDebtAmount(Long id) {
        return null;
    }

    @Override
    public BigDecimal returnSnapLateFeeAmount(Long id) {
        return null;
    }
}

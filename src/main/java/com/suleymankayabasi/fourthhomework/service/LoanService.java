package com.suleymankayabasi.fourthhomework.service;

import com.suleymankayabasi.fourthhomework.defaults.DefaultValue;
import com.suleymankayabasi.fourthhomework.dto.LoanDTO;
import com.suleymankayabasi.fourthhomework.mapper.LoanMapper;
import com.suleymankayabasi.fourthhomework.model.Loan;
import com.suleymankayabasi.fourthhomework.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class LoanService implements ILoanService{

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public LoanDTO save(LoanDTO loanDTO) {

        Loan loan = LoanMapper.INSTANCE.convertLoanDTOtoLoan(loanDTO);
        if(loan.getLoanType().equalsIgnoreCase(DefaultValue.loanNormal)){
            loan = loanRepository.save(loan);
            LoanDTO loanDTOResult = LoanMapper.INSTANCE.convertLoanToLoanDTO(loan);
            return loanDTOResult;
        }
        throw new RuntimeException("Loan type is not acceptable!");
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


    //todo zaman kavramına bi çözüm bul ya timestamp yap date yap ya da string yap
    /*public void calculateDateIsHowManyDays(Date date){

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        try{

            Date date1 = simpleDateFormat.parse(String.valueOf(date));

            long date1InMs = date1.getTime();

            int daysDiff = (int) (date1InMs / (1000 * 60 * 60* 24));
            System.out.println(daysDiff);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }*/
}

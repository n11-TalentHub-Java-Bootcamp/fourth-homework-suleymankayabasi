package com.suleymankayabasi.fourthhomework.service;

import com.suleymankayabasi.fourthhomework.dto.LoanDTO;
import com.suleymankayabasi.fourthhomework.enums.LoanTypeEnum;
import com.suleymankayabasi.fourthhomework.exception.LoanNotFoundException;
import com.suleymankayabasi.fourthhomework.exception.UserNotFoundException;
import com.suleymankayabasi.fourthhomework.mapper.LoanMapper;
import com.suleymankayabasi.fourthhomework.model.Loan;
import com.suleymankayabasi.fourthhomework.repository.LoanRepository;
import com.suleymankayabasi.fourthhomework.util.LoanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class LoanService implements ILoanService{

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public LoanDTO save(LoanDTO loanDTO) {

        Loan loan = LoanMapper.INSTANCE.convertLoanDTOtoLoan(loanDTO);
        if(loan.getLoanType().equalsIgnoreCase(LoanTypeEnum.STR_NORMAL.toString())){
            loan = loanRepository.save(loan);
            LoanDTO loanDTOResult = LoanMapper.INSTANCE.convertLoanToLoanDTO(loan);
            return loanDTOResult;
        }
        throw new LoanNotFoundException("Loan type is not acceptable!");
    }

    @Override
    public List<LoanDTO> listLoans(LocalDate firstDate, LocalDate lastDate) {

        List<Loan> loanList = loanRepository.findAll();
        List<Loan> newLoanList = new ArrayList<>();
        for(Loan loan: loanList){
            if(firstDate.isBefore(loan.getLoanDate()) && lastDate.isAfter(loan.getLoanDate())){
                newLoanList.add(loan);
            }
        }
        List<LoanDTO> loanDTOList = LoanMapper.INSTANCE.convertLoanListToLoanDTOList(newLoanList);
        if(loanDTOList.isEmpty()) throw new LoanNotFoundException("Loan List is empty");
        return loanDTOList;
    }

    @Override
    public List<LoanDTO> listAllLoansByUserId(Long id) {

        List<Loan> loanList = loanRepository.findAll();
        List<Loan> newLoanList = new ArrayList<>();
        for(Loan loan: loanList){
            if(loan.getUser().getUserId().equals(id)){
                if(loan.getArrears().compareTo(BigDecimal.valueOf(0)) > 0){
                    newLoanList.add(loan);
                }
            }
            else {
                throw new UserNotFoundException("User is not found.");
            }
        }
        List<LoanDTO> loanDTOList = LoanMapper.INSTANCE.convertLoanListToLoanDTOList(newLoanList);
        if(loanDTOList.isEmpty()) throw new LoanNotFoundException("Loan List is empty");
        return loanDTOList;
    }

    @Override
    public List<LoanDTO> listAllDueDateLoansByUserId(Long id) {

        List<Loan> loanList = loanRepository.findAll();
        List<Loan> newLoanList = new ArrayList<>();
        for (Loan loan : loanList) {
            if (loan.getUser().getUserId().equals(id)) {
                if (LoanUtils.isInvalidDueDate(loan.getDueDate())) {
                    if(loan.getArrears().compareTo(BigDecimal.valueOf(0)) > 0){
                        newLoanList.add(loan);
                    }
                }
            }
            else{
                throw new UserNotFoundException("User is not found.");
            }
        }

        List<LoanDTO> loanDTOList = LoanMapper.INSTANCE.convertLoanListToLoanDTOList(newLoanList);
        if (loanDTOList.isEmpty()) throw new LoanNotFoundException("Loan List is empty");
        return loanDTOList;
    }

    @Override
    public BigDecimal returnTotalDebtAmount(Long id) {

        List<Loan> loanList = loanRepository.findAll();
        BigDecimal sum = BigDecimal.valueOf(0);
        for (Loan loan: loanList){
            if(loan.getUser().getUserId().equals(id)){
                sum = sum.add(loan.getArrears());
            }
            else {
                throw new UserNotFoundException("User is not found.");
            }
        }
        return sum;
    }

    @Override
    public BigDecimal returnDueDateDebtAmount(Long id) {
        List<Loan> loanList = loanRepository.findAll();
        BigDecimal sum = BigDecimal.valueOf(0);
        for (Loan loan: loanList){
            if(loan.getUser().getUserId().equals(id)){
                if(LoanUtils.isInvalidDueDate(loan.getDueDate())){
                    sum = sum.add(loan.getArrears());
                }
            }
            else{
                throw new UserNotFoundException("User is not found.");
            }
        }
        return sum;
    }

    @Override
    public BigDecimal returnSnapLateFeeAmount(Long id) {

        List<Loan> loanList = loanRepository.findAll();
        BigDecimal sum = BigDecimal.valueOf(0);
        for (Loan loan: loanList){
            if(loan.getUser().getUserId().equals(id)){
                if(LoanUtils.isInvalidDueDate(loan.getDueDate()) && loan.getArrears().compareTo(BigDecimal.valueOf(0)) > 0){
                    sum = sum.add(LoanUtils.calculateLateFeeAmount(loan.getDueDate()));
                }
            }
            else{
                throw new UserNotFoundException("User is not found.");
            }
        }
        return sum;
    }

    public BigDecimal calculateLoanById(Long id) {
        Loan loan = loanRepository.findLoanByLoanId(id);
        return LoanUtils.calculateLoan(loan);
    }

    public LoanDTO findLoanById(Long id){
        Loan loan = loanRepository.findLoanByLoanId(id);
        if(loan.equals(null)) throw new LoanNotFoundException("Loan not found");
        LoanDTO loanDTO = LoanMapper.INSTANCE.convertLoanToLoanDTO(loan);
        return loanDTO;

    }

    protected LoanDTO updateLoanAmount(Long id){
        Loan loan = loanRepository.findLoanByLoanId(id);
        loan.setArrears(BigDecimal.valueOf(0));
        LoanDTO loanDTO = LoanMapper.INSTANCE.convertLoanToLoanDTO(loan);
        return  loanDTO;
    }
}

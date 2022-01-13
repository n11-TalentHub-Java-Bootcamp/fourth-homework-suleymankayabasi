package com.suleymankayabasi.fourthhomework.service;

import com.suleymankayabasi.fourthhomework.dto.LoanDTO;
import com.suleymankayabasi.fourthhomework.exception.LoanNotFoundException;
import com.suleymankayabasi.fourthhomework.exception.UserNotFoundException;
import com.suleymankayabasi.fourthhomework.mapper.LoanMapper;
import com.suleymankayabasi.fourthhomework.model.Loan;
import com.suleymankayabasi.fourthhomework.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LoanService implements ILoanService{

    private static final String loanNormal = "Normal";
    private static final String loanLateFee = "Late Fee";

    private static final double before2018 = 1.5;
    private static final double after2018 = 2.0;

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public LoanDTO save(LoanDTO loanDTO) {

        Loan loan = LoanMapper.INSTANCE.convertLoanDTOtoLoan(loanDTO);
        if(loan.getLoanType().equalsIgnoreCase(loanNormal)){
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
                if (isUnvalidDueDate(loan.getDueDate())) {
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
                if(isUnvalidDueDate(loan.getDueDate())){
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
        return null;
    }

    // vade tarihi 2018den önce mi
    private boolean isDateBeforeTwoThousandEighteen(LocalDate localDate){

        String twoThousandEighteenStr = "2018-01-01";
        LocalDate twoThousandEighteen = LocalDate.parse(twoThousandEighteenStr);
        return localDate.isBefore(twoThousandEighteen);
    }

    // 2018den önceki vade tarihi ile 2018 arasındaki gün sayısı
    private int calculateDayAmountBeforeTwoThousandEighteen(LocalDate localDate){

        String dateTwoThousandEighteenStr = "2018-01-01";
        LocalDate dateTwoThousandEighteen = LocalDate.parse(dateTwoThousandEighteenStr);
        return (int) ChronoUnit.DAYS.between(localDate, dateTwoThousandEighteen);
    }

    // 2018den sonraki vade tarihi ile 2018 arasındaki gün sayısı
    private int calculateDayAmountAfterTwoThousandEighteen(LocalDate localDate){

        String dateTwoThousandEighteenStr = "2018-01-01";
        LocalDate dateTwoThousandEighteen = LocalDate.parse(dateTwoThousandEighteenStr);
        return (int) ChronoUnit.DAYS.between(dateTwoThousandEighteen,localDate);
    }

    // geçikme zammı borcu döner
    private BigDecimal calculateLateFeeAmount(LocalDate dueDate) {

        LocalDate localDateNow = LocalDate.now();

        if (isDateBeforeTwoThousandEighteen(dueDate)) {
            int beforeday = calculateDayAmountBeforeTwoThousandEighteen(dueDate);
            double before = before2018 * beforeday;
            int  afterday = calculateDayAmountAfterTwoThousandEighteen(localDateNow);
            double after = after2018 * afterday;
            BigDecimal result = BigDecimal.valueOf(before + after);
            return result;

        } else{
            int  afterday = calculateDayAmountAfterTwoThousandEighteen(localDateNow);
            double after = after2018 * afterday;
            BigDecimal result = BigDecimal.valueOf( after);
            return result;

        }
    }

    // vadesi geçmiş mi bunu kontrol ediyor
    private boolean isUnvalidDueDate(LocalDate dueDate){
        LocalDate nowDate = LocalDate.now();
        return  dueDate.isBefore(nowDate);
    }

    public BigDecimal calculateLoan(Loan loan){

        BigDecimal mainDebt = loan.getPrincipalDebt();
        //vade tarihine bakıyor borcun
        if(isUnvalidDueDate(loan.getDueDate())){
            //ana tutar + gecikme faizi --> toplam borç
            return mainDebt.add(calculateLateFeeAmount(loan.getDueDate()));
        }

        return mainDebt;
    }
}

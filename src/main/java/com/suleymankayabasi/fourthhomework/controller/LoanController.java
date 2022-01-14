package com.suleymankayabasi.fourthhomework.controller;

import com.suleymankayabasi.fourthhomework.dto.LoanDTO;
import com.suleymankayabasi.fourthhomework.model.Loan;
import com.suleymankayabasi.fourthhomework.service.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private ILoanService loanService;

    @GetMapping
    public ResponseEntity<LoanDTO> findLoanByLoanId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(loanService.findLoanById(id));
    }

    @PostMapping
    public ResponseEntity<LoanDTO> saveLoan(@RequestBody @Valid LoanDTO loanDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.save(loanDTO));
    }

    @GetMapping("/loansByDate")
    public List<LoanDTO> findAll( @RequestParam(name= "dateBefore", defaultValue="")String dateBeforeString,
                                   @RequestParam(name= "dateAfter", defaultValue="")String dateAfterString){
        String pattern ="yyyy-MM-dd";
        LocalDate dateBefore = LocalDate.parse(dateBeforeString, DateTimeFormatter.ofPattern(pattern));
        LocalDate dateAfter = LocalDate.parse(dateAfterString, DateTimeFormatter.ofPattern(pattern));
        return loanService.listLoans(dateBefore,dateAfter);
    }

    @GetMapping("/list-loans-user/{id}")
    public List<LoanDTO> listAllLoansByUserId(@PathVariable Long id){
        return loanService.listAllLoansByUserId(id);
    }

    @GetMapping("/due-date-loans-list-user/{id}")
    public List<LoanDTO> listAllDueDateLoansByUserId(@PathVariable Long id){
        return loanService.listAllDueDateLoansByUserId(id);
    }

    @GetMapping("/arrears-total/{id}")
    public ResponseEntity<BigDecimal> returnTotalDebtAmount(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(loanService.returnTotalDebtAmount(id));
    }

    @GetMapping("/due-date-loans-total/{id}")
    public ResponseEntity<BigDecimal> returnDueDateDebtAmount(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(loanService.returnDueDateDebtAmount(id));
    }

    @GetMapping("/late-fee-total/{id}")
    public ResponseEntity<BigDecimal> returnSnapLateFeeAmount(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(loanService.returnSnapLateFeeAmount(id));
    }
}

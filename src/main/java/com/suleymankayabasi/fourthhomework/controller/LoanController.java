package com.suleymankayabasi.fourthhomework.controller;

import com.suleymankayabasi.fourthhomework.dto.LoanDTO;
import com.suleymankayabasi.fourthhomework.service.LoanService;
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
    private LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanDTO> saveLoan(@RequestBody @Valid LoanDTO loanDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.save(loanDTO));
    }

    @GetMapping("/total-loan-amount/{id}")
    public ResponseEntity<BigDecimal> calculateLoan(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(loanService.calculateLoanById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> findLoanByLoanId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(loanService.findLoanById(id));
    }

    @GetMapping("/loan-by-date")
    public List<LoanDTO> findAll( @RequestParam(name= "dateBefore", defaultValue="")String dateBeforeString,
                                   @RequestParam(name= "dateAfter", defaultValue="")String dateAfterString){
        String pattern ="yyyy-MM-dd";
        LocalDate dateBefore = LocalDate.parse(dateBeforeString, DateTimeFormatter.ofPattern(pattern));
        LocalDate dateAfter = LocalDate.parse(dateAfterString, DateTimeFormatter.ofPattern(pattern));
        return loanService.listLoans(dateBefore,dateAfter);
    }

    @GetMapping("/loan-list/user/{id}")
    public List<LoanDTO> listAllLoansByUserId(@PathVariable Long id){
        return loanService.listAllLoansByUserId(id);
    }

    @GetMapping("/due-date-loan-list/user/{id}")
    public List<LoanDTO> listAllDueDateLoansByUserId(@PathVariable Long id){
        return loanService.listAllDueDateLoansByUserId(id);
    }

    @GetMapping("/total-arrears/user/{id}")
    public ResponseEntity<BigDecimal> returnTotalDebtAmount(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(loanService.returnTotalDebtAmount(id));
    }

    @GetMapping("/total-due-date-loan/user/{id}")
    public ResponseEntity<BigDecimal> returnDueDateDebtAmount(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(loanService.returnDueDateDebtAmount(id));
    }

    @GetMapping("/total-late-fee-amount/user/{id}")
    public ResponseEntity<BigDecimal> returnSnapLateFeeAmount(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(loanService.returnSnapLateFeeAmount(id));
    }
}

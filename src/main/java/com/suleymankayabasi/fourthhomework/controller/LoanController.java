package com.suleymankayabasi.fourthhomework.controller;

import com.suleymankayabasi.fourthhomework.dto.LoanDTO;
import com.suleymankayabasi.fourthhomework.service.ILoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private ILoanService loanService;

    @PostMapping
    private ResponseEntity<LoanDTO> saveLoan(@RequestBody @Valid LoanDTO loanDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.save(loanDTO));
    }
}

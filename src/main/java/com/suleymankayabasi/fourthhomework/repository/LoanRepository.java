package com.suleymankayabasi.fourthhomework.repository;

import com.suleymankayabasi.fourthhomework.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository  extends JpaRepository<Loan,Long> {

    Loan findLoanByLoanId(Long id);
}

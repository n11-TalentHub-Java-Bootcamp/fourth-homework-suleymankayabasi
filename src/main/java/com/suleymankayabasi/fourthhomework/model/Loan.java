package com.suleymankayabasi.fourthhomework.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="LOANS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LOAN_ID",nullable = false)
    private Long loanId;

    @Column(name= "PRINCIPAL_DEBT",precision=10, scale=2,updatable = false)
    private BigDecimal principalDebt;

    @Column(name = "ARREARS", precision = 10, scale = 2)
    private BigDecimal arrears;

    @Column(name = "LOAN_DATE",columnDefinition = "DATE")
    private LocalDate loanDate;

    @Column(name = "DUE_DATE",columnDefinition = "DATE")
    private LocalDate dueDate;

    @Column(name = "LOAN_TYPE")
    private String loanType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOAN_USER",foreignKey = @ForeignKey(name = "FK_LOAN_USER_ID"))
    private User user;

}

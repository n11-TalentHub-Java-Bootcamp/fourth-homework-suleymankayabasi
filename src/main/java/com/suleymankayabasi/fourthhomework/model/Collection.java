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
@Table(name="COLLECTIONS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Collection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long collectionid;

    @Column(name = "REGISTRATION_DATE",columnDefinition = "DATE")
    private LocalDate registrationDate;

    @Column(name= "PRINCIPAL_DEBT_AMOUNT",precision=10, scale=2,updatable = false)
    private BigDecimal principalDebtAmount;

    @Column(name= "LOAN_AMOUNT",precision=10, scale=2)
    private BigDecimal loanAmount;

    @Column(name = "COLLECTION_LOAN_TYPE")
    private String collectionLoanType;

    @Column(name = "LOAN_ID")
    private Long loanId;

    @Column(name = "USER_ID")
    private Long loanUserId;
}



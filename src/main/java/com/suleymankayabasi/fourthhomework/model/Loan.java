package com.suleymankayabasi.fourthhomework.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

    //Ana Borç Tutarı kaydedildikten sonra güncellenemez.
    @Column(name= "PRINCIPAL_DEBT",precision=10, scale=2,updatable = false)
    private BigDecimal principalDebt;

    //Kalan borç
    @Column(name = "ARREARS", precision = 10, scale = 2)
    private BigDecimal arrears;

    //Borca ait bir “vade tarihi” alanı olmalıdır.
    @Column(name = "DUE_DATE")
    @Temporal(TemporalType.DATE)
    private Date dueDate;

    @Column(name = "LOAN_TYPE")
    private String loanType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOAN_USER",foreignKey = @ForeignKey(name = "FK_LOAN_USER_ID"))
    private User user;

    @Transient
    private BigDecimal lateFeeAmount;
    //Borç sorgulama servisi sonucunda gecikme zammı hesaplanmalı fakat databasede “gecikme zammı” alanı tutulmamalıdır

}

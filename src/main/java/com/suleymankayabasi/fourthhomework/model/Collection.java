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
@Table(name="COLLECTIONS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Collection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long collectionId;

    @Column(name = "REGISTRATION_DATE")
    @Temporal(TemporalType.DATE)
    private Date registrationDate;  //Kayıt tarihi tahsilat yapılan tarih olur.

    // ana borç varsa geçikme borcunu dagöstermeli
    @Column(name= "PRINCIPAL_DEBT_AMOUNT",precision=10, scale=2)
    private BigDecimal principalDebtAmount;

    @Column(name= "LOAN_AMOUNT",precision=10, scale=2)
    private BigDecimal loanAmount;

    @Column(name = "LOAN_TYPE")
    private String loanType;

    @Column(name = "LOAN_ID")
    private Long loanId;
    //Bağlı olduğu borç bilgisi muhakkak tutulmalıdır ki hangi borca istinaden bu gecikme zammı oluşmuş, görünebilsin.

    @Column(name = "USER_ID")
    private Long loanUserId;
}



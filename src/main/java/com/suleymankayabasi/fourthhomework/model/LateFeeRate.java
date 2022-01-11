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
@Table(name="LATE_FEE_RATES")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LateFeeRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LATE_FEE_ID",nullable = false)
    private Long lateFeeId;

    @Column(name = "LATE_FEE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lateFeeDate;

    @Column(name = "LATE_FEE_AMOUNT", precision = 10, scale = 2)
    private BigDecimal lateFeeAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LATE_FEE_LOAN",foreignKey = @ForeignKey(name = "FK_LATE_FEE_LOAN_ID"))
    private Loan loan;
}

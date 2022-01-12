package com.suleymankayabasi.fourthhomework.enums;

import java.math.BigDecimal;

public enum LATE_FEE_RATE {

    BEFORE2018(1.5),AFTER2018(2.0);

    public BigDecimal lateFeeRate;

    LATE_FEE_RATE(double lateFeeRate){
        this.lateFeeRate = BigDecimal.valueOf(lateFeeRate);
    }

    public BigDecimal getLateFeeRate() {
        return lateFeeRate;
    }
}

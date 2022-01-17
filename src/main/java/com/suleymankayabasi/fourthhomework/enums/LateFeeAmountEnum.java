package com.suleymankayabasi.fourthhomework.enums;

public enum LateFeeAmountEnum {

    BEFORE_2018(1.5),
    AFTER_2018(2.0);

    private final double value;
    
    LateFeeAmountEnum(final double value) {
        this.value = value;
    }

    public double getVal() {
        return value;
    }
}

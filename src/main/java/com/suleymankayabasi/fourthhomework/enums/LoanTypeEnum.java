package com.suleymankayabasi.fourthhomework.enums;

public enum LoanTypeEnum {

    STR_NORMAL("Normal"),
    STR_LATE_FEE("Late_Fee"),
    STR_TOTAL("Total")
    ;

    private final String text;

    LoanTypeEnum(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

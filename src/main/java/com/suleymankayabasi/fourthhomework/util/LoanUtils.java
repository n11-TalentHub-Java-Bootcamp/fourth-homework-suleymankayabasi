package com.suleymankayabasi.fourthhomework.util;

import com.suleymankayabasi.fourthhomework.enums.LateFeeAmountEnum;
import com.suleymankayabasi.fourthhomework.model.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LoanUtils {

    private LoanUtils () {}

    public static boolean isInvalidDueDate(LocalDate dueDate){
        LocalDate nowDate = LocalDate.now();
        return  dueDate.isBefore(nowDate);
    }

    public static BigDecimal calculateLoan(Loan loan){

        BigDecimal mainDebt = loan.getPrincipalDebt();
        if(LoanUtils.isInvalidDueDate(loan.getDueDate())){
            return mainDebt.add(calculateLateFeeAmount(loan.getDueDate()));
        }
        return mainDebt;
    }

    public static boolean isDateBeforeTwoThousandEighteen(LocalDate localDate){

        String twoThousandEighteenStr = "2018-01-01";
        LocalDate twoThousandEighteen = LocalDate.parse(twoThousandEighteenStr);
        return localDate.isBefore(twoThousandEighteen);
    }


    public static int calculateDayAmountBeforeTwoThousandEighteen(LocalDate localDate){

        String dateTwoThousandEighteenStr = "2018-01-01";
        LocalDate dateTwoThousandEighteen = LocalDate.parse(dateTwoThousandEighteenStr);
        return (int) ChronoUnit.DAYS.between(localDate, dateTwoThousandEighteen);
    }

    public static int calculateDayAmountAfterTwoThousandEighteenDueDate(LocalDate dueDate){
        LocalDate date = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(dueDate,date);
    }


    public static int calculateDayAmountAfterTwoThousandEighteen(LocalDate localDate){

        String dateTwoThousandEighteenStr = "2018-01-01";
        LocalDate dateTwoThousandEighteen = LocalDate.parse(dateTwoThousandEighteenStr);
        return (int) ChronoUnit.DAYS.between(dateTwoThousandEighteen,localDate);
    }

    public static BigDecimal calculateLateFeeAmount(LocalDate dueDate) {

        LocalDate localDateNow = LocalDate.now();

        if (isDateBeforeTwoThousandEighteen(dueDate)) {
            int beforeday = calculateDayAmountBeforeTwoThousandEighteen(dueDate);
            double before = LateFeeAmountEnum.BEFORE_2018.getVal() * beforeday;
            int  afterday = calculateDayAmountAfterTwoThousandEighteen(localDateNow);
            double after = LateFeeAmountEnum.AFTER_2018.getVal() * afterday;
            BigDecimal result = BigDecimal.valueOf(before + after);
            return result;

        } else{
            int  afterday = calculateDayAmountAfterTwoThousandEighteenDueDate(dueDate);
            double after = LateFeeAmountEnum.AFTER_2018.getVal() * afterday;
            BigDecimal result = BigDecimal.valueOf( after);
            return result;

        }
    }


}

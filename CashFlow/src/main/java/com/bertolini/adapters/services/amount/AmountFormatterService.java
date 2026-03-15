package com.bertolini.adapters.services.amount;

import java.math.BigDecimal;

public class AmountFormatterService {
    private final AmountCleaner amountCleaner;

    public AmountFormatterService(AmountCleaner amountCleaner) {
        this.amountCleaner = amountCleaner;
    }

    public BigDecimal getFormattedAmount(String amount) {
        amount = amount.trim();
        String amountStr = amountCleaner.clean(amount);

        amountStr = changeToComma(amountStr);
        System.out.println(amountStr);
        return new BigDecimal(amountStr);
    }

    private String changeToComma(String amount) {
        int len = amount.length();
        char signal;

        if (len <= 2)
            return amount;

        int commaIndex = amount.lastIndexOf(',');
        int dotIndex = amount.lastIndexOf('.');
        int index = Math.max(commaIndex, dotIndex);

        if (commaIndex > dotIndex)
            signal = ',';
        else
            signal = '.';

//        System.out.println(amount + " -> " + signal);

        if (signal == ',') {
            return  amount.replaceAll("\\.", "")
                    .replaceAll(",", ".");

        } else return amount.replaceAll(",", "");

    }
}

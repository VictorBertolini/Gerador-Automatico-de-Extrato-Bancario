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

        return new BigDecimal(amountStr);
    }

    private String changeToComma(String amount) {
        int len = amount.length();

        if (len <= 2)
            return amount;

        char signal = amount.charAt(len - 3);
        if (signal == ',') {
            StringBuilder sb = new StringBuilder(amount);
            sb.setCharAt(len - 3, '.');

            return sb.toString();
        } else if (signal == '.') {
            return amount.replaceAll(",", "");
        }
        throw new RuntimeException("Cannot identify the " + signal + " symbol");
    }
}

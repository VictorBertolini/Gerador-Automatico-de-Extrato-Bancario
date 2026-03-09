package com.bertolini.adapters.services.amount;


public class AmountCleaner {
    public String clean(String amount) {
        return amount.replaceAll("[^0-9+-.,]", "");
    }
}

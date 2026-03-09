package com.bertolini.core.domain.entitys;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private int id;
    private String bank;
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String transactionType;
    private BigDecimal amount;
//    private boolean isNegative;

    public Transaction() {

    }
    public Transaction(int id, String bank, LocalDate date, LocalTime time, String description, String transactionType, BigDecimal amount, boolean isNegative) {
        this.id = id;
        this.bank = bank;
        this.date = date;
        this.time = time;
        this.description = description;
        this.transactionType = transactionType;
        this.amount = amount;
    }
    public Transaction(String bank, LocalDate date, LocalTime time, String description, String transactionType, BigDecimal amount) {
        this.bank = bank;
        this.date = date;
        this.time = time;
        this.description = description;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getBank() {
        return bank;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}

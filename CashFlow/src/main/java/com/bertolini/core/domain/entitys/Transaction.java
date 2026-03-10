package com.bertolini.core.domain.entitys;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HexFormat;

public class Transaction {
    private String bank;
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String transactionType;
    private BigDecimal amount;

    public Transaction() {

    }
    public Transaction(String bank, LocalDate date, LocalTime time, String description, String transactionType, BigDecimal amount, boolean isNegative) {
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

    public String getId() {
        return getFingerPrint();
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


    private String getFingerPrint() {
        String raw = getString();
        return encrypt(raw);
    }

    private String getString() {
        return this.getDate().toString()
                + "|" + this.getAmount().stripTrailingZeros().toPlainString()
                + "|" + this.getDescription().trim().toLowerCase();
    }

    private String encrypt(String raw) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            return raw;
        }
    }
}

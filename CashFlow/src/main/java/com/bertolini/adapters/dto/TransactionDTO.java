package com.bertolini.adapters.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
public class TransactionDTO {
    private String bank;
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String transactionType;
    private BigDecimal amount;
    private boolean isNegative;
}

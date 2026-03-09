package com.bertolini.adapters.dto;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;


public record TransactionDTO(
    String bank,
    LocalDate date,
    LocalTime time,
    String description,
    String transactionType,
    BigDecimal amount
) {

}

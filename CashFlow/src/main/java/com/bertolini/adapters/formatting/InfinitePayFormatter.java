package com.bertolini.adapters.formatting;

import com.bertolini.adapters.dto.TransactionDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class InfinitePayFormatter implements DataFormatter {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Override
    public ArrayList<TransactionDTO> formatAll(ArrayList<String> bankStatementData) {
        ArrayList<TransactionDTO> transactions = new ArrayList<>();
        for (String line : bankStatementData) {
            transactions.add(format(line));
        }
        return transactions;
    }

    @Override
    public TransactionDTO format(String bankStatementLine) {
        // YYYY-MM-DD,HH:MM:SS, <TransactionType>, <Description>, <adicional info>, "+R$ A.BBB,CC"
        String[] data = bankStatementLine.split(",");

        LocalDate date = formatDate(data[0]);
        LocalTime time = formatTime(data[1]);
        String transactionType = data[2];
        String description = data[3];
        String value = String.join(",", Arrays.copyOfRange(data, 5, data.length));
        BigDecimal amount = formatAmount(value);

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .date(date)
                .time(time)
                .transactionType(transactionType)
                .bank("InfinitePay")
                .description(description)
                .amount(amount)
                .build();

        return transactionDTO;
    }

    private LocalDate formatDate(String date) {
        String cleanDate = date.replace("-", "/");
        return LocalDate.parse(cleanDate, formatter);
    }

    private LocalTime formatTime(String time) {
        return LocalTime.parse(time);
    }

    private BigDecimal formatAmount(String amount) {
        // Cleaning
        String cleanAmount = amount.replace(" ", "")
                .replace("+", "")
                .replace("R$", "")
                .replace("\"", "")
                .replace(".", "")
                .replace(",", ".");

        return new BigDecimal(cleanAmount);
    }
}


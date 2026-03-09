package com.bertolini.adapters.formatting;

import com.bertolini.adapters.dto.TransactionDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class CsvFormatter implements DataFormatter {
    private final ArrayList<String> fieldOrder;
    private final String bankName;

    public CsvFormatter(ArrayList<String> fieldOrder, String bankName) {
        this.fieldOrder = fieldOrder;
        this.bankName = bankName;
    }

    @Override
    public ArrayList<TransactionDTO> formatAll(ArrayList<String> bankStatementData, String sep) {
        ArrayList<TransactionDTO> transactionDTOS = new ArrayList<>();
        for (String line: bankStatementData) {
            transactionDTOS.add(format(line, sep));
        }
        return transactionDTOS;
    }

    @Override
    public TransactionDTO format(String bankStatementLine, String sep) {
        String[] splitedLine = bankStatementLine.split(sep);


        HashMap<String, String> fieldMap = new HashMap<>();
        for (int i = 0; i < fieldOrder.size(); i++) {
            fieldMap.put(fieldOrder.get(i), splitedLine[i]);
        }

        LocalDate date = LocalDate.parse(fieldMap.get("date"));
        LocalTime time = fieldMap.containsKey("time") ? LocalTime.parse(fieldMap.get("time")) : null;
        String description = fieldMap.getOrDefault("description", null);
        String transactionType = fieldMap.getOrDefault("type", null);
        BigDecimal amount = new BigDecimal(fieldMap.get("amount"));

        return new TransactionDTO(
                        bankName,
                        date,
                        time,
                        description,
                        transactionType,
                        amount
                );

    }
}

package com.bertolini.adapters.formatting;

import com.bertolini.adapters.dto.TransactionDTO;
import com.bertolini.adapters.services.amount.AmountFormatterService;
import com.bertolini.adapters.services.date.DateFormatterService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.HashMap;

public class CsvFormatter implements DataFormatter {
    private final ArrayList<String> fieldOrder;
    private final String bankName;
    private DateFormatterService dateFormatter;
    private AmountFormatterService amountFormatter;

    public CsvFormatter(ArrayList<String> fieldOrder, String bankName, DateFormatterService dateFormatter, AmountFormatterService amountFormatter) {
        this.fieldOrder = fieldOrder;
        this.bankName = bankName;
        this.dateFormatter = dateFormatter;
        this.amountFormatter = amountFormatter;
    }

    @Override
    public ArrayList<TransactionDTO> formatAll(ArrayList<String> bankStatementData, String sep, boolean useCommaAsAmountSeparator) {
        ArrayList<TransactionDTO> transactionDTOS = new ArrayList<>();
        for (String line: bankStatementData) {
            transactionDTOS.add(format(line, sep, useCommaAsAmountSeparator));
        }
        return transactionDTOS;
    }



    @Override
    public TransactionDTO format(String bankStatementLine, String sep, boolean useCommaAsAmountSeparator) {
        String[] splitedLine;
        if (useCommaAsAmountSeparator && sep.equals(",")) {
            // Split by comma, but if find something between " " just ignore commas inside
            splitedLine = bankStatementLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        } else {
            splitedLine = bankStatementLine.split(sep);
        }

        HashMap<String, String> fieldMap = new HashMap<>();
        for (int i = 0; i < fieldOrder.size(); i++) {
            fieldMap.put(fieldOrder.get(i), splitedLine[i]);
        }

        LocalDate date = dateFormatter.getFormattedDate(fieldMap.get("date"));
        LocalTime time = fieldMap.containsKey("time") ? LocalTime.parse(fieldMap.get("time")) : null;
        String description = fieldMap.getOrDefault("description", null);
        String transactionType = fieldMap.getOrDefault("type", null);
        BigDecimal amount = amountFormatter.getFormattedAmount(fieldMap.get("amount"));


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

package com.bertolini.adapters.controllers;

import com.bertolini.adapters.dto.TransactionDTO;

import com.bertolini.adapters.formatting.DataFormatter;

import java.util.ArrayList;

public class DataFormatterController {
    private final DataFormatter dataFormatter;

    public DataFormatterController(DataFormatter dataFormatter) {
        this.dataFormatter = dataFormatter;
    }

    public ArrayList<TransactionDTO> formatBankStatementTransactions(ArrayList<String> bankStatementData, String sep) {
        return dataFormatter.formatAll(bankStatementData, sep);
    }

    public TransactionDTO formatBankStatementTransaction(String bankStatementData, String sep) {
        return dataFormatter.format(bankStatementData, sep);
    }
}

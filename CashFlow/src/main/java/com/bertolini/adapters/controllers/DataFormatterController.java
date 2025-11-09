package com.bertolini.adapters.controllers;

import com.bertolini.adapters.dto.TransactionDTO;

import com.bertolini.adapters.formatting.DataFormatter;

import java.util.ArrayList;

public class DataFormatterController {
    private DataFormatter dataFormatter;

    public DataFormatterController(DataFormatter dataFormatter) {
        this.dataFormatter = dataFormatter;
    }

    public ArrayList<TransactionDTO> formatBankStatementTransactions(ArrayList<String> bankStatementData) {
        return dataFormatter.formatAll(bankStatementData);
    }

    public TransactionDTO formatBankStatementTransaction(String bankStatementData) {
        return dataFormatter.format(bankStatementData);
    }
}

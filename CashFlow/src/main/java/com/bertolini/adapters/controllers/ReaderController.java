package com.bertolini.adapters.controllers;

import com.bertolini.core.useCases.reader.GetBankStatementDataCase;

import java.util.ArrayList;

public class ReaderController {
    private GetBankStatementDataCase getBankTransactions;

    public ReaderController(GetBankStatementDataCase getBankTransactions) {
        this.getBankTransactions = getBankTransactions;
    }

    public ArrayList<String> getBankStatementData(String fileName) {
        return getBankTransactions.execute(fileName);
    }
}

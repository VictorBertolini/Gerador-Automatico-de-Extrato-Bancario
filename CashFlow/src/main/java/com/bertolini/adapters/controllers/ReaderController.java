package com.bertolini.adapters.controllers;

import com.bertolini.core.useCases.reader.bankstatement.GetBankStatementDataCase;

import java.util.ArrayList;

public class ReaderController {
    private GetBankStatementDataCase getBankTransactions;

    public ReaderController(GetBankStatementDataCase getBankTransactions) {
        this.getBankTransactions = getBankTransactions;
    }

    public ArrayList<String> getBankStatementData(String fileName, boolean hasHeader) {
        return getBankTransactions.execute(fileName, hasHeader);
    }
}

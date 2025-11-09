package com.bertolini.core.useCases.reader;


import java.util.ArrayList;

public class GetBankStatementDataCase {
    private BankStatementReader bankStatementReader;

    public GetBankStatementDataCase(BankStatementReader bankStatementReader) {
        this.bankStatementReader = bankStatementReader;
    }

    public ArrayList<String> execute(String fileName) {
        return bankStatementReader.readTransactions(fileName);
    }
}

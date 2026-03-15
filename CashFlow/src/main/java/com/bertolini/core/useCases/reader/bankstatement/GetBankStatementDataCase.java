package com.bertolini.core.useCases.reader.bankstatement;


import java.util.ArrayList;

public class GetBankStatementDataCase {
    private BankStatementReader bankStatementReader;

    public GetBankStatementDataCase(BankStatementReader bankStatementReader) {
        this.bankStatementReader = bankStatementReader;
    }

    public ArrayList<String> execute(String fileName, boolean hasHeader) {
        return bankStatementReader.readTransactions(fileName, hasHeader);
    }
}

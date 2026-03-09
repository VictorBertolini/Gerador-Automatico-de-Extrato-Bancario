package com.bertolini.core.useCases.reader;

import java.util.ArrayList;

public interface BankStatementReader {
    public ArrayList<String> readTransactions(String fileName, boolean header);
}

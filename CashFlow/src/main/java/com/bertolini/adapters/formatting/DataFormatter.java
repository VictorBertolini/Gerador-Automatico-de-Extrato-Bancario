package com.bertolini.adapters.formatting;

import com.bertolini.adapters.dto.TransactionDTO;

import java.util.ArrayList;

public interface DataFormatter {
    public ArrayList<TransactionDTO> formatAll(ArrayList<String> bankStatementData);
    public TransactionDTO format(String bankStatementLine);
}

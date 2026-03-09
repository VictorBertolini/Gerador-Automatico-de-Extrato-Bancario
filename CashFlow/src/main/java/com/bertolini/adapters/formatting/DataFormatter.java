package com.bertolini.adapters.formatting;

import com.bertolini.adapters.dto.TransactionDTO;

import java.util.ArrayList;

public interface DataFormatter {
    ArrayList<TransactionDTO> formatAll(ArrayList<String> bankStatementData, String sep);

    TransactionDTO format(String bankStatementLine, String sep);
}

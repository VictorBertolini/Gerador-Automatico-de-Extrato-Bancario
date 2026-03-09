package com.bertolini.adapters.services;

import com.bertolini.adapters.controllers.DataFormatterController;
import com.bertolini.adapters.controllers.ReaderController;
import com.bertolini.adapters.dto.TransactionDTO;
import com.bertolini.adapters.mappers.TransactionMapper;
import com.bertolini.core.domain.entitys.Transaction;

import java.util.ArrayList;

public class BankIntegrationService {
    private ReaderController readerController;
    private DataFormatterController dataFormatterController;
    private TransactionMapper mapper;

    public BankIntegrationService(ReaderController readerController, DataFormatterController dataFormatterController, TransactionMapper mapper) {
        this.readerController = readerController;
        this.dataFormatterController = dataFormatterController;
        this.mapper = mapper;
    }

    public ArrayList<Transaction> importBankTransactions(String fileName, boolean header, String sep, boolean useCommaAsAmountSeparator) {
        ArrayList<String> data = readerController.getBankStatementData(fileName, header);
        ArrayList<TransactionDTO> dtos = dataFormatterController.formatBankStatementTransactions(data, sep, useCommaAsAmountSeparator);
        return mapper.toDomain(dtos);
    }
}

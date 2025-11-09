package com.bertolini.app;

import com.bertolini.adapters.controllers.DataFormatterController;
import com.bertolini.adapters.controllers.ReaderController;
import com.bertolini.adapters.controllers.RepositoryController;
import com.bertolini.adapters.controllers.TransactionController;
import com.bertolini.adapters.dto.TransactionDTO;
import com.bertolini.adapters.formatting.InfinitePayFormatter;
import com.bertolini.adapters.mappers.TransactionMapper;
import com.bertolini.adapters.services.BankIntegrationService;
import com.bertolini.core.domain.entitys.Transaction;
import com.bertolini.core.domain.entitys.TransactionBatch;
import com.bertolini.core.domain.entitys.TransactionSet;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        String FILE_NAME = "BankStatement.csv";

        AppConfig config = new AppConfig();
        TransactionSet transactionSet = new TransactionSet();
        TransactionMapper mapper = new TransactionMapper();

        // Configuration
        ReaderController readerController = config.buildReaderController();
        RepositoryController repositoryController = config.buildRepositoryController(transactionSet, "InfinitePay_CashFlow");
        TransactionController transactionController = config.buildTransactionController(transactionSet);
        DataFormatterController dataFormatterController = config.buildDataFormatterController(new InfinitePayFormatter());

        // Logic of the program
        BankIntegrationService bankIntegrationService = new BankIntegrationService(readerController, dataFormatterController, mapper);

        ArrayList<Transaction> transactions = bankIntegrationService.importBankTransactions(FILE_NAME);
        transactionSet.addTransactions(transactions);


        ArrayList<TransactionBatch> transactionBatches = transactionController.splitTransactionsByMonth(transactionSet);

        // Save
        repositoryController.saveInPersistence(transactionBatches);
    }
}

package com.bertolini.app;

import com.bertolini.adapters.controllers.DataFormatterController;
import com.bertolini.adapters.controllers.ReaderController;
import com.bertolini.adapters.controllers.RepositoryController;
import com.bertolini.adapters.controllers.TransactionController;
import com.bertolini.adapters.formatting.DataFormatter;
import com.bertolini.adapters.mappers.TransactionMapper;
import com.bertolini.adapters.services.BankIntegrationService;
import com.bertolini.core.domain.entitys.Transaction;
import com.bertolini.core.domain.entitys.TransactionBatch;
import com.bertolini.core.domain.entitys.TransactionSet;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    static String FILE_NAME = "Nubank_Fevereiro.csv";
    static String XLSX_FILE_NAME = "Nubank_Statement";
    static ArrayList<String> fieldOrder = new ArrayList<>(Arrays.asList("date", "amount", null, "description"));
    static String bankName = "Nubank";
    static String sep = ",";
    static DataFormatter dataFormatter;

    // Controllers
    static ReaderController readerController;
    static RepositoryController repositoryController;
    static TransactionController transactionController;
    static DataFormatterController dataFormatterController;

    public static void main(String[] args) {
        AppConfig config = new AppConfig();
        TransactionSet transactionSet = new TransactionSet();
        configControllers(config, transactionSet);

        // Logic of the program
        TransactionMapper mapper = new TransactionMapper();
        BankIntegrationService bankIntegrationService = new BankIntegrationService(readerController, dataFormatterController, mapper);

        ArrayList<Transaction> transactions = bankIntegrationService.importBankTransactions(FILE_NAME, true, sep, true);
        transactionSet.addTransactions(transactions);


        ArrayList<TransactionBatch> transactionBatches = transactionController.splitTransactionsByMonth(transactionSet);

        // Save
        repositoryController.saveInPersistence(transactionBatches);
    }

    public static void configControllers(AppConfig config, TransactionSet transactionSet) {
        dataFormatter = config.buildDataFormatter(fieldOrder, bankName);
        readerController = config.buildReaderController();
        repositoryController = config.buildRepositoryController(XLSX_FILE_NAME, transactionSet);
        transactionController = config.buildTransactionController(transactionSet);
        dataFormatterController = config.buildDataFormatterController(dataFormatter);
    }

}

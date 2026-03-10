package com.bertolini.app;

import com.bertolini.adapters.controllers.DataFormatterController;
import com.bertolini.adapters.controllers.ReaderController;
import com.bertolini.adapters.controllers.RepositoryController;
import com.bertolini.adapters.controllers.TransactionController;
import com.bertolini.adapters.formatting.DataFormatter;
import com.bertolini.adapters.services.amount.AmountCleaner;
import com.bertolini.adapters.formatting.CsvFormatter;
import com.bertolini.adapters.services.amount.AmountFormatterService;
import com.bertolini.adapters.services.date.DateFormatDetector;
import com.bertolini.adapters.services.date.DateFormatterService;
import com.bertolini.adapters.mappers.TransactionMapper;
import com.bertolini.adapters.services.BankIntegrationService;
import com.bertolini.core.domain.entitys.Transaction;
import com.bertolini.core.domain.entitys.TransactionBatch;
import com.bertolini.core.domain.entitys.TransactionSet;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

//        String FILE_NAME = "BankStatement.csv";
//        String FILE_NAME = "Nubank_Janeiro.csv";
        String FILE_NAME = "Nubank_Fevereiro.csv";
        String XLSX_FILE_NAME = "Nubank_Statement";

        AppConfig config = new AppConfig();
        TransactionSet transactionSet = new TransactionSet();
        TransactionMapper mapper = new TransactionMapper();

        // InfinitePay
//        ArrayList<String> fieldOrder = new ArrayList<>(Arrays.asList("date", "time", "type", "description", null, "amount"));

        // Nubank
        ArrayList<String> fieldOrder = new ArrayList<>(Arrays.asList("date", "amount", null, "description"));
        String bankName = "Nubank";

        String sep = ",";

        // Configuration
        DataFormatter dataFormatter = config.buildDataFormatter(fieldOrder, bankName);

        ReaderController readerController = config.buildReaderController();
        RepositoryController repositoryController = config.buildRepositoryController(transactionSet, XLSX_FILE_NAME);
        TransactionController transactionController = config.buildTransactionController(transactionSet);
        DataFormatterController dataFormatterController = config.buildDataFormatterController(dataFormatter);

        // Logic of the program
        BankIntegrationService bankIntegrationService = new BankIntegrationService(readerController, dataFormatterController, mapper);

        ArrayList<Transaction> transactions = bankIntegrationService.importBankTransactions(FILE_NAME, true, sep, true);
        transactionSet.addTransactions(transactions);


        ArrayList<TransactionBatch> transactionBatches = transactionController.splitTransactionsByMonth(transactionSet);

        // Save
        repositoryController.saveInPersistence(transactionBatches);
    }
}

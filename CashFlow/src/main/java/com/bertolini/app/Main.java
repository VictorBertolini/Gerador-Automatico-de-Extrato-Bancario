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

    // =========================================================
    //   BANK CONFIGURATION — edit here to change the import
    // =========================================================

    static final String CSV_FILE          = "BankStatementExample.csv";
    static final String XLSX_FILE         = "CashFlowExample";
    static final String BANK_NAME         = "InfinitePay";
    static final String CSV_SEPARATOR     = ",";
    static final boolean SKIP_HEADER      = true;
    static final boolean COMMA_AS_DECIMAL = true;

    // Field order in your CSV — use null to skip a column
    // Available fields: "date", "time", "type", "description", "amount"
    static final ArrayList<String> FIELD_ORDER = new ArrayList<>(
            Arrays.asList("date", "time", "type", "description", null, "amount") // InfinitePay
            //  Arrays.asList("date", "amount", null, "description")                 // Nubank
    );

    // =========================================================
    //   Controllers — no need to touch below this line
    // =========================================================

    static ReaderController readerController;
    static RepositoryController repositoryController;
    static TransactionController transactionController;
    static DataFormatterController dataFormatterController;

    public static void main(String[] args) {
        AppConfig config = new AppConfig();
        TransactionSet transactionSet = new TransactionSet();
        configControllers(config, transactionSet);

        BankIntegrationService bankIntegrationService = new BankIntegrationService(
                readerController, dataFormatterController, new TransactionMapper()
        );

        ArrayList<Transaction> transactions = bankIntegrationService
                .importBankTransactions(CSV_FILE, SKIP_HEADER, CSV_SEPARATOR, COMMA_AS_DECIMAL);

        transactionSet.addTransactions(transactions);

        ArrayList<TransactionBatch> batches = transactionController
                .splitTransactionsByMonth(transactionSet);

        repositoryController.saveInPersistence(batches);
    }

    private static void configControllers(AppConfig config, TransactionSet transactionSet) {
        DataFormatter dataFormatter     = config.buildDataFormatter(FIELD_ORDER, BANK_NAME);
        readerController                = config.buildReaderController();
        repositoryController            = config.buildRepositoryController(XLSX_FILE);
        transactionController           = config.buildTransactionController(transactionSet);
        dataFormatterController         = config.buildDataFormatterController(dataFormatter);
    }
}

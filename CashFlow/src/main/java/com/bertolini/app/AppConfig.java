package com.bertolini.app;

import com.bertolini.adapters.controllers.DataFormatterController;
import com.bertolini.adapters.controllers.ReaderController;
import com.bertolini.adapters.controllers.RepositoryController;
import com.bertolini.adapters.controllers.TransactionController;
import com.bertolini.adapters.formatting.CsvFormatter;
import com.bertolini.adapters.formatting.DataFormatter;
import com.bertolini.adapters.readers.CsvReader;
import com.bertolini.adapters.respositories.excel.ExcelRepository;
import com.bertolini.adapters.respositories.excel.style.ExcelTheme;
import com.bertolini.adapters.services.amount.AmountCleaner;
import com.bertolini.adapters.services.amount.AmountFormatterService;
import com.bertolini.adapters.services.date.DateFormatDetector;
import com.bertolini.adapters.services.date.DateFormatterService;
import com.bertolini.core.domain.entitys.TransactionSet;
import com.bertolini.core.useCases.reader.bankstatement.BankStatementReader;
import com.bertolini.core.useCases.reader.bankstatement.GetBankStatementDataCase;
import com.bertolini.core.useCases.repository.SaveAllTransactionsCase;
import com.bertolini.core.useCases.repository.TransactionRepository;
import com.bertolini.core.useCases.transactions.CreateTransactionCase;
import com.bertolini.core.useCases.transactions.RemoveTransactionCase;
import com.bertolini.core.useCases.transactions.SplitTransactionSetByMonthCase;

import java.util.ArrayList;

public class AppConfig {
    public ReaderController buildReaderController() {
        BankStatementReader reader = new CsvReader();
        GetBankStatementDataCase getBankTransCase = new GetBankStatementDataCase(reader);
        ReaderController controller = new ReaderController(getBankTransCase);
        return controller;
    }

    public RepositoryController buildRepositoryController(String fileName) {
        ExcelTheme theme = ExcelTheme.Builder.defaultTheme();
        TransactionRepository repository = new ExcelRepository(fileName, theme);
        SaveAllTransactionsCase saveAllCase = new SaveAllTransactionsCase(repository);
        return new RepositoryController(repository, saveAllCase);
    }

    public TransactionController buildTransactionController(TransactionSet transactionSet) {
        CreateTransactionCase createCase = new CreateTransactionCase(transactionSet);
        RemoveTransactionCase removeCase = new RemoveTransactionCase(transactionSet);
        SplitTransactionSetByMonthCase groupCase = new SplitTransactionSetByMonthCase();
        TransactionController controller = new TransactionController(createCase, removeCase, groupCase);
        return controller;
    }

    public DataFormatterController  buildDataFormatterController(DataFormatter dataFormatter) {
        return  new DataFormatterController(dataFormatter);
    }

    public DataFormatter buildDataFormatter(ArrayList<String> fieldOrder, String bankName) {
        // Date
        DateFormatDetector dateFormatDetector = new DateFormatDetector();
        DateFormatterService dateFormatter = new DateFormatterService(dateFormatDetector);

        // Amount
        AmountCleaner amountCleaner = new AmountCleaner();
        AmountFormatterService amountFormatter = new AmountFormatterService(amountCleaner);

        return new CsvFormatter(fieldOrder, bankName, dateFormatter, amountFormatter);
    }
}

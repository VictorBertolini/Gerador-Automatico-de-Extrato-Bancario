package com.bertolini.app;

import com.bertolini.adapters.controllers.DataFormatterController;
import com.bertolini.adapters.controllers.ReaderController;
import com.bertolini.adapters.controllers.RepositoryController;
import com.bertolini.adapters.controllers.TransactionController;
import com.bertolini.adapters.formatting.DataFormatter;
import com.bertolini.adapters.readers.infinitePay.InfinitePayReader;
import com.bertolini.adapters.respositories.excel.ExcelRepository;
import com.bertolini.core.domain.entitys.TransactionSet;
import com.bertolini.core.useCases.reader.BankStatementReader;
import com.bertolini.core.useCases.reader.GetBankStatementDataCase;
import com.bertolini.core.useCases.repository.SaveAllTransactionsCase;
import com.bertolini.core.useCases.repository.TransactionRepository;
import com.bertolini.core.useCases.transactions.CreateTransactionCase;
import com.bertolini.core.useCases.transactions.RemoveTransactionCase;
import com.bertolini.core.useCases.transactions.SplitTransactionSetByMonthCase;

public class AppConfig {
    public ReaderController buildReaderController() {
        BankStatementReader reader = new InfinitePayReader();
        GetBankStatementDataCase getBankTransCase = new GetBankStatementDataCase(reader);
        ReaderController controller = new ReaderController(getBankTransCase);
        return controller;
    }

    public RepositoryController buildRepositoryController(TransactionSet transactionSet, String fileName) {
        TransactionRepository repository = new ExcelRepository(fileName);
        SaveAllTransactionsCase saveAllCase = new SaveAllTransactionsCase(repository);
        RepositoryController controller = new RepositoryController(transactionSet, saveAllCase);
        return controller;
    }

    public TransactionController buildTransactionController(TransactionSet transactionSet) {
        CreateTransactionCase createCase = new CreateTransactionCase(transactionSet);
        RemoveTransactionCase removeCase = new RemoveTransactionCase(transactionSet);
        SplitTransactionSetByMonthCase groupCase = new SplitTransactionSetByMonthCase();
        TransactionController  controller = new TransactionController(createCase, removeCase, groupCase);
        return controller;
    }

    public DataFormatterController  buildDataFormatterController(DataFormatter dataFormatter) {
        return  new DataFormatterController(dataFormatter);
    }

}

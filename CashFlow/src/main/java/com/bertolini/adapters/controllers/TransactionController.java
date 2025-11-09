package com.bertolini.adapters.controllers;

import com.bertolini.core.domain.entitys.Transaction;
import com.bertolini.core.domain.entitys.TransactionBatch;
import com.bertolini.core.domain.entitys.TransactionSet;
import com.bertolini.core.useCases.transactions.CreateTransactionCase;
import com.bertolini.core.useCases.transactions.RemoveTransactionCase;
import com.bertolini.core.useCases.transactions.SplitTransactionSetByMonthCase;

import java.util.ArrayList;

public class TransactionController {
    private CreateTransactionCase createTransactionCase;
    private RemoveTransactionCase removeTransactionCase;
    private SplitTransactionSetByMonthCase splitTransactionSetByMonthCase;

    public TransactionController(
            CreateTransactionCase createTransactionCase,
            RemoveTransactionCase removeTransactionCase,
            SplitTransactionSetByMonthCase splitTransactionSetByMonthCase
    ) {
        this.createTransactionCase = createTransactionCase;
        this.removeTransactionCase = removeTransactionCase;
        this.splitTransactionSetByMonthCase = splitTransactionSetByMonthCase;
    }

    public void createTransaction(Transaction transaction) {
        createTransactionCase.execute(transaction);
    }

    public void createTransactions(TransactionSet transactionSet) {
        createTransactionCase.execute(transactionSet);
    }

    public void removeTransaction(Transaction transaction) {
        removeTransactionCase.execute(transaction);
    }

    public ArrayList<TransactionBatch> splitTransactionsByMonth(TransactionSet transactionSet) {
        return splitTransactionSetByMonthCase.execute(transactionSet);
    }
}

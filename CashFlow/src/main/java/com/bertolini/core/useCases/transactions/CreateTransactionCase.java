package com.bertolini.core.useCases.transactions;

import com.bertolini.core.domain.entitys.Transaction;
import com.bertolini.core.domain.entitys.TransactionSet;

public class CreateTransactionCase {
    private final TransactionSet transactionSet;

    public CreateTransactionCase(TransactionSet transactionSet) {
        this.transactionSet = transactionSet;
    }

    public void execute(Transaction trans) {
        transactionSet.addTransaction(trans);
    }

    public void execute(TransactionSet transactionSet) {
        transactionSet.addTransactions(transactionSet.getTransactions());
    }
}

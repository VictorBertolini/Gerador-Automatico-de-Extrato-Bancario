package com.bertolini.core.useCases.transactions;

import com.bertolini.core.domain.entitys.Transaction;
import com.bertolini.core.domain.entitys.TransactionSet;

public class RemoveTransactionCase {
    private final TransactionSet transactionSet;

    public RemoveTransactionCase(TransactionSet transactionSet) {
        this.transactionSet = transactionSet;
    }

    public void execute(Transaction trans) {
        transactionSet.removeTransaction(trans);
    }
}

package com.bertolini.core.useCases.transactions;

import com.bertolini.core.domain.entitys.Transaction;
import com.bertolini.core.domain.entitys.TransactionSet;
import java.util.Set;

public class FilterDuplicateTransactionCase {

    public TransactionSet execute(TransactionSet incoming, Set<String> existingIds) {
        TransactionSet filtered = new TransactionSet();
        for (Transaction t : incoming.getTransactions()) {
            if (!existingIds.contains(t.getId())) {
                filtered.addTransaction(t);
            }
        }
        return filtered;
    }
}

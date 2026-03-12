package com.bertolini.core.useCases.transactions;

import com.bertolini.core.domain.entitys.Transaction;
import com.bertolini.core.domain.entitys.TransactionBatch;
import com.bertolini.core.domain.entitys.TransactionSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterDuplicateTransactionCase {

    public TransactionSet execute(TransactionSet incoming, List<TransactionBatch> existing) {

        Set<String> fingerprints = new HashSet<>();

        for (TransactionBatch batch : existing) {
            for (Transaction t : batch.getTransactionSet().getTransactions()) {
                fingerprints.add(t.getId());
            }
        }

        TransactionSet filtered = new TransactionSet();
        for (Transaction t : incoming.getTransactions()) {
            if (!fingerprints.contains(t.getId())) {
                filtered.addTransaction(t);
            }
        }

        return filtered;
    }
}

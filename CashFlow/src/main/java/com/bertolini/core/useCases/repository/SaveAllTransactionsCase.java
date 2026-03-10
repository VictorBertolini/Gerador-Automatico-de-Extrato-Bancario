package com.bertolini.core.useCases.repository;

import com.bertolini.core.domain.entitys.TransactionBatch;

import java.util.List;

public class SaveAllTransactionsCase {
    private TransactionRepository repository;

    public SaveAllTransactionsCase(TransactionRepository repository) {
        this.repository = repository;
    }

    public void execute(TransactionBatch transactionBatch) {
        repository.save(transactionBatch);
    }

    public void execute(List<TransactionBatch> transactionBatches) {
        repository.saveAll(transactionBatches);
    }
}

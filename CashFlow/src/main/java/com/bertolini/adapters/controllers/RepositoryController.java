package com.bertolini.adapters.controllers;

import com.bertolini.core.domain.entitys.TransactionBatch;
import com.bertolini.core.domain.entitys.TransactionSet;
import com.bertolini.core.useCases.repository.SaveAllTransactionsCase;
import com.bertolini.core.useCases.repository.TransactionRepository;
import com.bertolini.core.useCases.transactions.FilterDuplicateTransactionCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RepositoryController {
    private SaveAllTransactionsCase saveAllUseCase;
    private final FilterDuplicateTransactionCase filterCase;
    private TransactionRepository repository;

    public RepositoryController(TransactionRepository repository, SaveAllTransactionsCase saveAllUseCase) {
        this.saveAllUseCase = saveAllUseCase;
        this.filterCase = new FilterDuplicateTransactionCase();
        this.repository = repository;
    }

    public void saveInPersistence(TransactionBatch transactionBatch) {
        saveAllUseCase.execute(transactionBatch);
    }

    public void saveInPersistence(List<TransactionBatch> batches) {
        List<TransactionBatch> filteredBatches = new ArrayList<>();

        for (TransactionBatch batch : batches) {
            Set<String> existingIds = repository.getIdsByLabel(batch.getLabel());
            TransactionSet filtered = filterCase.execute(batch.getTransactionSet(), existingIds);

            if (!filtered.getTransactions().isEmpty()) {
                filteredBatches.add(new TransactionBatch(batch.getLabel(), batch.getMonth(), batch.getYear(), filtered));
            }
        }
        saveAllUseCase.execute(filteredBatches);
    }

}

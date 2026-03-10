package com.bertolini.adapters.controllers;

import com.bertolini.core.domain.entitys.TransactionBatch;
import com.bertolini.core.domain.entitys.TransactionSet;
import com.bertolini.core.useCases.repository.SaveAllTransactionsCase;

import java.util.ArrayList;
import java.util.List;

public class RepositoryController {
    private SaveAllTransactionsCase saveAllUseCase;

    public RepositoryController(TransactionSet transactionSet, SaveAllTransactionsCase saveAllUseCase) {
        this.saveAllUseCase = saveAllUseCase;
    }

    public void saveInPersistence(TransactionBatch transactionBatch) {
        saveAllUseCase.execute(transactionBatch);
    }

    public void saveInPersistence(List<TransactionBatch> transactionBatches) {
        saveAllUseCase.execute(transactionBatches);
    }
}

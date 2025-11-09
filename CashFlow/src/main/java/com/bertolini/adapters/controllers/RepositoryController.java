package com.bertolini.adapters.controllers;

import com.bertolini.core.domain.entitys.TransactionBatch;
import com.bertolini.core.domain.entitys.TransactionSet;
import com.bertolini.core.useCases.repository.SaveAllTransactionsCase;

import java.util.ArrayList;

public class RepositoryController {
    private SaveAllTransactionsCase saveAllUseCase;

    public RepositoryController(TransactionSet transactionSet, SaveAllTransactionsCase saveAllUseCase) {
        this.saveAllUseCase = saveAllUseCase;
    }

    public void saveInPersistence(TransactionBatch transactionBatch) {
        saveAllUseCase.execute(transactionBatch);
    }

    public void saveInPersistence(ArrayList<TransactionBatch> transactionBatches) {
        for  (TransactionBatch transactionBatch : transactionBatches) {
            if (transactionBatch.getTransactionSet() != null) {
                saveAllUseCase.execute(transactionBatch);
            }
        }
    }
}

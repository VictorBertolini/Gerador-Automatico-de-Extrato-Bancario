package com.bertolini.core.useCases.repository;

import com.bertolini.core.domain.entitys.TransactionBatch;

import java.util.List;

public class GetCashFlowTransactionsId {
    private final TransactionRepository repository;

    public GetCashFlowTransactionsId(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<TransactionBatch> execute() {
        return repository.getAll();
    }
}

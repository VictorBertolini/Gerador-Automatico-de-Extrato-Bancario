package com.bertolini.core.useCases.repository;

import com.bertolini.core.domain.entitys.TransactionBatch;

public interface TransactionRepository {
    public void save(TransactionBatch transactionBatch);
}

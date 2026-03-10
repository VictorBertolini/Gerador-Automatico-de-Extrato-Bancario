package com.bertolini.core.useCases.repository;

import com.bertolini.core.domain.entitys.TransactionBatch;

import java.util.List;

public interface TransactionRepository {
    void save(TransactionBatch transactionBatch);
    void saveAll(List<TransactionBatch> transactionBatches);
}

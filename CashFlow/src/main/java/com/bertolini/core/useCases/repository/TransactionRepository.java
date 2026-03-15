package com.bertolini.core.useCases.repository;

import com.bertolini.core.domain.entitys.TransactionBatch;

import java.util.List;
import java.util.Set;

public interface TransactionRepository {
    void save(TransactionBatch transactionBatch);
    void saveAll(List<TransactionBatch> transactionBatches);
    Set<String> getIdsByLabel(String label);
}

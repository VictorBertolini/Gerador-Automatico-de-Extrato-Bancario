package com.bertolini.core.domain.entitys;

public class TransactionBatch {
    private String label;
    private TransactionSet transactionSet;

    public TransactionBatch() {
        transactionSet = new TransactionSet();
    }

    public TransactionBatch(String label) {
        this.label = label;
        transactionSet = new TransactionSet();
    }

    public TransactionBatch(String label, TransactionSet transactionSet) {
        this.label = label;
        this.transactionSet = transactionSet;
    }

    public String getLabel() {
        return label;
    }

    public TransactionSet getTransactionSet() {
        return transactionSet;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setTransactionSet(TransactionSet transactionSet) {
        this.transactionSet = transactionSet;
    }
}

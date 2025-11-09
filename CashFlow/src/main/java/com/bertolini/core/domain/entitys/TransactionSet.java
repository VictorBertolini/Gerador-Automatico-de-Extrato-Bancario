package com.bertolini.core.domain.entitys;

import java.util.ArrayList;

public class TransactionSet {
    ArrayList<Transaction> transactions;

    public TransactionSet() {
        transactions = new ArrayList<>();
    }

    public void addTransactions(ArrayList<Transaction> transactions) {
        this.transactions.addAll(transactions);
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}

package com.bertolini.core.useCases.transactions;

import com.bertolini.core.domain.entitys.Transaction;
import com.bertolini.core.domain.entitys.TransactionBatch;
import com.bertolini.core.domain.entitys.TransactionSet;

import java.util.ArrayList;
import java.util.HashMap;

public class SplitTransactionSetByMonthCase {

    private static final String[] months = {
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public ArrayList<TransactionBatch> execute(TransactionSet transactionSet) {
        HashMap<String, TransactionBatch> map = new HashMap<>();
        int monthValue;
        int yearValue;

        for  (Transaction transaction : transactionSet.getTransactions()) {
            monthValue = transaction.getDate().getMonthValue() - 1;
            yearValue = transaction.getDate().getYear();

            String hashKey = months[monthValue] + " " + yearValue;

            if (!map.containsKey(hashKey)) {
                map.put(hashKey, new TransactionBatch(hashKey, monthValue, yearValue, new TransactionSet()));
            }

            map.get(months[monthValue] + " " + yearValue).getTransactionSet().addTransaction(transaction);
        }

        ArrayList<TransactionBatch> transactionBatches = new ArrayList<>();
        for (TransactionBatch transactionBatch : map.values()) {
            transactionBatches.add(transactionBatch);
        }

        return transactionBatches;
    }
}

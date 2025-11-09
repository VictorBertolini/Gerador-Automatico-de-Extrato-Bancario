package com.bertolini.adapters.mappers;

import com.bertolini.adapters.dto.TransactionDTO;
import com.bertolini.core.domain.entitys.Transaction;

import java.util.ArrayList;

public class TransactionMapper {

    public Transaction toDomain(TransactionDTO dto) {
        return new Transaction(dto.getBank(), dto.getDate(), dto.getTime(), dto.getDescription(), dto.getTransactionType(), dto.getAmount(), dto.isNegative());
    }

    public ArrayList<Transaction> toDomain(ArrayList<TransactionDTO> dtos) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (TransactionDTO dto : dtos) {
            transactions.add(toDomain(dto));
        }
        return transactions;
    }
}

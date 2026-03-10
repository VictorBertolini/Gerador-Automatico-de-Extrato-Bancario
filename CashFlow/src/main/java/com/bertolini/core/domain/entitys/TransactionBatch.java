package com.bertolini.core.domain.entitys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionBatch {
    private int month;
    private int year;
    private TransactionSet transactionSet;
}

package com.bertolini.adapters.respositories.excel;

import com.bertolini.core.domain.entitys.TransactionSet;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.math.BigDecimal;

public class ExcelTransactionWriter {
    private Sheet sheet;
    private Row row;

    public ExcelTransactionWriter(Sheet sheet) {
        this.sheet = sheet;
    }

    public void write(TransactionSet transactionSet) {
        for (int i = 0; i < transactionSet.getTransactions().size(); i++) {
            row = sheet.getRow(i + 3);
            if (row == null) {
                row = sheet.createRow(i + 3);
            }
            row.createCell(1).setCellValue(transactionSet.getTransactions().get(i).getId());
            row.createCell(2).setCellValue(transactionSet.getTransactions().get(i).getBank());
            row.createCell(3).setCellValue(transactionSet.getTransactions().get(i).getDate().toString());
            row.createCell(4).setCellValue(transactionSet.getTransactions().get(i).getDescription());

            row.createCell(5).setCellValue(transactionSet.getTransactions().get(i).getAmount().doubleValue());
        }
    }
}

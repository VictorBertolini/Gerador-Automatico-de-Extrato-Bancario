package com.bertolini.adapters.respositories.test;

import com.bertolini.adapters.respositories.excel.service.LastExcelRowGetter;
import com.bertolini.adapters.respositories.excel.structure.Cell;
import com.bertolini.adapters.respositories.excel.style.ExcelLayout;
import com.bertolini.core.domain.entitys.Transaction;
import com.bertolini.core.domain.entitys.TransactionSet;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.math.BigDecimal;

public class ExcelTransactionWriter {
    private final Sheet sheet;
    private final LastExcelRowGetter lastExcelRowGetter = new LastExcelRowGetter();
    public ExcelTransactionWriter(Sheet sheet) {
        this.sheet = sheet;
    }

    public void write(TransactionSet transactionSet) {
        Cell startCell = lastExcelRowGetter.getLastRowNumber(sheet);
//        int startRow = Math.max(sheet.getLastRowNum(), ExcelLayout.START_DATA_FILL.row());


        for (int i = 0; i < transactionSet.getTransactions().size(); i++) {
            Transaction t = transactionSet.getTransactions().get(i);
            Row row = sheet.getRow(startCell.row() + i);
            if (row == null)
                row = sheet.createRow(startCell.row() + i);


            for (int col = 0; col < ExcelLayout.HEADER_COLUMNS.length; col++) {
                String fieldKey = ExcelLayout.HEADER_COLUMNS[col][0];
                String field    = extractField(t, fieldKey);

                if (field != null) {
                    org.apache.poi.ss.usermodel.Cell cell = row.createCell(ExcelLayout.START_DATA_FILL.column() + col);

                    if (fieldKey.equals("amount")) {
                        cell.setCellValue(new BigDecimal(field).doubleValue());
                    } else {
                        cell.setCellValue(field);
                    }
                }
            }
        }
    }

    private String extractField(Transaction t, String key) {
        return switch (key) {
            case "id"              -> String.valueOf(t.getId());
            case "bank"            -> t.getBank();
            case "date"            -> t.getDate() != null ? t.getDate().toString() : null;
            case "time"            -> t.getTime() != null ? t.getTime().toString() : null;
            case "description"     -> t.getDescription();
            case "transactionType" -> t.getTransactionType();
            case "amount"          -> t.getAmount() != null ? t.getAmount().toPlainString() : null;
            default                -> null;
        };
    }
}

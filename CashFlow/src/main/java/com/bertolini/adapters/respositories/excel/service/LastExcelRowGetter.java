package com.bertolini.adapters.respositories.excel.service;

import com.bertolini.adapters.respositories.excel.structure.Cell;
import com.bertolini.adapters.respositories.excel.style.ExcelLayout;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class LastExcelRowGetter {
    public Cell getLastRowNumber(Sheet sheet) {
        int idIndexRow = ExcelLayout.START_DATA_FILL.row();
        int idIndexColumn = ExcelLayout.START_DATA_FILL.column();
        Row row;

        int limit = Math.max(ExcelLayout.NET_CASH.row(), Math.max(ExcelLayout.INFLOW.row(), ExcelLayout.OUTFLOW.row()));

        for (int i = idIndexRow; i <= limit; i++) {
            row = sheet.getRow(i);
            if (row.getCell(idIndexColumn) == null || row.getCell(idIndexColumn).getCellType() == CellType.BLANK) {
                return new Cell(i, idIndexColumn);
            }
        }
        return new Cell(sheet.getLastRowNum() ,idIndexColumn);
    }
}

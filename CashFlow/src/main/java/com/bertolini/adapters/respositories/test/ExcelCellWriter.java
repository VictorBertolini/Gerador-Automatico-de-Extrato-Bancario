package com.bertolini.adapters.respositories.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelCellWriter {

    public ExcelCellWriter() {

    }

    private Cell checkPosition(Sheet sheet, int rowIndex, int cellIndex) {
        Row row = sheet.getRow(rowIndex);
        if (row == null)
            row = sheet.createRow(rowIndex);

        Cell cell = row.getCell(cellIndex);
        if (cell == null)
            cell = row.createCell(cellIndex);

        return cell;
    }

    public void applyValueInCell(Sheet sheet, int rowIndex, int cellIndex, String value) {
        Cell cell = checkPosition(sheet, rowIndex, cellIndex);
        cell.setCellValue(value);
    }

    public void applyFormulaInCell(Sheet sheet, int rowIndex, int cellIndex, String formula) {
        Cell cell =  checkPosition(sheet, rowIndex, cellIndex);
        cell.setCellFormula(formula);
    }

    public void applyStyleInCell(Sheet sheet, int rowIndex, int cellIndex, CellStyle style) {
        Cell cell  = checkPosition(sheet, rowIndex, cellIndex);
        cell.setCellStyle(style);
    }
}
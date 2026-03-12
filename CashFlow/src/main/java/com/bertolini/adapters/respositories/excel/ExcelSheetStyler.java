package com.bertolini.adapters.respositories.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelSheetStyler {
    private Workbook workbook;
    private Sheet sheet;
    CellDecorator decorator;
    ExcelCellWriter excelWriter = new ExcelCellWriter();

    public static final int TITLE_ROW_INDEX = 0;
    public static final int TITLE_COLUMN_INDEX = 1;
    public static final String SHEET_NAME = "Cash Flow";
    public static final int HEADER_ROW_INDEX = 2;
    public static final int HEADER_COLUMN_INDEX = 1;
    public static final int RESULT_ROW_INDEX = 2;
    public static final int RESULT_COLUMN_INDEX = 6;
    public static final int NET_CASH_ROW_INDEX = 5;
    public static final int NET_CASH_COLUMN_INDEX = 6;


    public ExcelSheetStyler(Workbook workbook, Sheet sheet) {
        this.workbook = workbook;
        this.sheet = sheet;
        styleSheet();
    }

    public void styleSheet() {
        decorator = new CellDecorator(workbook);
        mergeCells();

        applyTitleStyle();

        applyHeaderStyle();

        applyResultCellsStyle();

        applyFormulas();

        applyBorders();

        applyColumnsSize();

        sheet.setZoom(160);
    }

    private void mergeCells() {
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 4));
        sheet.addMergedRegion(new CellRangeAddress(5, 5, 6, 7));
        sheet.addMergedRegion(new CellRangeAddress(6, 6, 6, 7));
    }

    private void applyTitleStyle() {
        excelWriter.applyValueInCell(sheet, TITLE_ROW_INDEX, TITLE_COLUMN_INDEX, SHEET_NAME);
        excelWriter.applyStyleInCell(sheet, TITLE_ROW_INDEX, TITLE_COLUMN_INDEX,decorator.titleStyle());
    }

    private void applyHeaderStyle() {
        String[] text = {"Id","Bank", "Date", "Description", "Amount"};
        CellStyle style = decorator.headerStyle();

        for (int i = 0; i < text.length; i++) {
            excelWriter.applyValueInCell(sheet, HEADER_ROW_INDEX, HEADER_COLUMN_INDEX + i, text[i]);
            excelWriter.applyStyleInCell(sheet, HEADER_ROW_INDEX, HEADER_COLUMN_INDEX + i, style);
        }
    }

    private void applyResultCellsStyle() {
        excelWriter.applyValueInCell(sheet,  RESULT_ROW_INDEX, RESULT_COLUMN_INDEX, "Inflow");
        excelWriter.applyStyleInCell(sheet, RESULT_ROW_INDEX, RESULT_COLUMN_INDEX, decorator.resultCellsStyle(IndexedColors.DARK_GREEN));

        excelWriter.applyValueInCell(sheet,  RESULT_ROW_INDEX, RESULT_COLUMN_INDEX + 1, "Outflow");
        excelWriter.applyStyleInCell(sheet, RESULT_ROW_INDEX, RESULT_COLUMN_INDEX + 1, decorator.resultCellsStyle(IndexedColors.DARK_RED));

        excelWriter.applyValueInCell(sheet, NET_CASH_ROW_INDEX, NET_CASH_COLUMN_INDEX, "Net Cash");
        excelWriter.applyStyleInCell(sheet, NET_CASH_ROW_INDEX, NET_CASH_COLUMN_INDEX, decorator.headerStyle());
    }

    private void applyColumnsSize() {
        int[] sizes = {5, 15, 15, 35, 15, 5, 13, 13};
        for (int i = 0; i < sizes.length; i++) {
            sheet.setColumnWidth(i, sizes[i] * 256);
        }
    }

    private void applyBorders() {
        CellStyle style = decorator.allBordersStyle();

        excelWriter.applyStyleInCell(sheet, 3, 6, style);
        excelWriter.applyStyleInCell(sheet, 3, 7, style);
        excelWriter.applyStyleInCell(sheet, 6, 6, style);
        excelWriter.applyStyleInCell(sheet, 6, 7, style);
    }

    private void applyFormulas() {
        CellStyle style = decorator.resultCellsStyle(IndexedColors.WHITE);

        excelWriter.applyStyleInCell(sheet, RESULT_ROW_INDEX + 1, RESULT_COLUMN_INDEX, style);
        excelWriter.applyFormulaInCell(sheet, RESULT_ROW_INDEX + 1, RESULT_COLUMN_INDEX, "SUMIF(E:E,\">0\")");

        excelWriter.applyStyleInCell(sheet, RESULT_ROW_INDEX + 1, RESULT_COLUMN_INDEX + 1, style);
        excelWriter.applyFormulaInCell(sheet, RESULT_ROW_INDEX + 1, RESULT_COLUMN_INDEX + 1, "SUMIF(E:E,\"<0\")");

        excelWriter.applyStyleInCell(sheet, RESULT_ROW_INDEX + 4, RESULT_COLUMN_INDEX, style);
        excelWriter.applyFormulaInCell(sheet, RESULT_ROW_INDEX + 4, RESULT_COLUMN_INDEX, "SUM(E:E)");
    }
}

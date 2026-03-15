package com.bertolini.adapters.respositories.excel.style;

import com.bertolini.adapters.respositories.excel.structure.Cell;
import com.bertolini.adapters.respositories.test.CellDecorator;
import com.bertolini.adapters.respositories.test.ExcelCellWriter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelSheetStyler {
    private final Workbook workbook;
    private final Sheet sheet;
    private final CellDecorator decorator;
    private final ExcelCellWriter writer = new ExcelCellWriter();

    public ExcelSheetStyler(Workbook workbook, Sheet sheet, ExcelTheme theme) {
        this.workbook  = workbook;
        this.sheet     = sheet;
        this.decorator = new CellDecorator(workbook, theme);
        styleSheet();
    }

    private void styleSheet() {
        mergeCells();
        applyTitle();
        applyHeaders();
        applyResultCells();
        applyFormulas();
        applyBorders();
        applyColumnSizes();
        sheet.setZoom(ExcelLayout.ZOOM_LEVEL);
    }

    private void mergeCells() {
        for (CellRangeAddress range : ExcelLayout.MERGE_REGIONS) {
            sheet.addMergedRegion(new CellRangeAddress(range.getFirstRow(), range.getLastRow(), range.getFirstColumn(), range.getLastColumn()));
        }
    }

    private void applyTitle() {
        int row = ExcelLayout.TITLE.row();
        int col = ExcelLayout.TITLE.column();
        writer.applyValueInCell(sheet, row, col, ExcelLayout.SHEET_TITLE);
        writer.applyStyleInCell(sheet, row, col, decorator.titleStyle());
    }

    private void applyHeaders() {
        CellStyle style = decorator.headerStyle();
        int row = ExcelLayout.HEADER.row();
        int col = ExcelLayout.HEADER.column();

        for (int i = 0; i < ExcelLayout.HEADER_COLUMNS.length; i++) {
            String label = ExcelLayout.HEADER_COLUMNS[i][1];
            writer.applyValueInCell(sheet, row, col + i, label);
            writer.applyStyleInCell(sheet, row, col + i, style);
        }
    }

    private void applyResultCells() {
        int inflowRow = ExcelLayout.INFLOW.row();
        int inflowCol = ExcelLayout.INFLOW.column();

        int outflowRow = ExcelLayout.OUTFLOW.row();
        int outflowCol = ExcelLayout.OUTFLOW.column();

        writer.applyValueInCell(sheet, inflowRow, inflowCol,     ExcelLayout.INFLOW_LABEL);
        writer.applyStyleInCell(sheet, inflowRow, inflowCol,     decorator.resultCellsStyle(theme().getInflowColor()));

        writer.applyValueInCell(sheet, outflowRow, outflowCol, ExcelLayout.OUTFLOW_LABEL);
        writer.applyStyleInCell(sheet, outflowRow, outflowCol, decorator.resultCellsStyle(theme().getOutflowColor()));

        writer.applyValueInCell(sheet, ExcelLayout.NET_CASH.row(), ExcelLayout.NET_CASH.column(), ExcelLayout.NETCASH_LABEL);
        writer.applyStyleInCell(sheet, ExcelLayout.NET_CASH.row(), ExcelLayout.NET_CASH.column(), decorator.headerStyle());
    }

    private void applyFormulas() {
        int inflowRow = ExcelLayout.INFLOW.row();
        int inflowCol = ExcelLayout.INFLOW.column();

        int outflowRow = ExcelLayout.OUTFLOW.row();
        int outflowCol = ExcelLayout.OUTFLOW.column();

        int netcashRow = ExcelLayout.NET_CASH.row();
        int netcashCol = ExcelLayout.NET_CASH.column();

        CellStyle inflowStyle  = decorator.resultCellsStyle(theme().getInflowColor());
        CellStyle outflowStyle = decorator.resultCellsStyle(theme().getOutflowColor());
        CellStyle netcashStyle = decorator.resultCellsStyle(theme().getNetcashFontColor());

        // +1 in row to be under the text
        writer.applyStyleInCell(sheet,   inflowRow + 1, inflowCol,     inflowStyle);
        writer.applyFormulaInCell(sheet, inflowRow + 1, inflowCol,     "SUMIF(H:H,\">0\")");

        writer.applyStyleInCell(sheet,   outflowRow + 1, outflowCol, outflowStyle);
        writer.applyFormulaInCell(sheet, outflowRow + 1, outflowCol, "SUMIF(H:H,\"<0\")");

        writer.applyStyleInCell(sheet,   netcashRow + 1, netcashCol, netcashStyle);
        writer.applyFormulaInCell(sheet, netcashRow + 1, netcashCol, "SUM(H:H)");
    }

    private void applyBorders() {
        CellStyle style = decorator.allBordersStyle();
        for (Cell cell : ExcelLayout.BORDER_CELLS) {
            writer.applyStyleInCell(sheet, cell.row(), cell.column(), style);
        }
    }

    private void applyColumnSizes() {
        for (int i = 0; i < ExcelLayout.HEADER_COLUMNS.length; i++) {
            int width = ExcelLayout.COLUMNS_WIDTH[i];
            sheet.setColumnWidth(i, width * 256);
        }
    }

    private ExcelTheme theme() {
        return decorator.getTheme();
    }
}

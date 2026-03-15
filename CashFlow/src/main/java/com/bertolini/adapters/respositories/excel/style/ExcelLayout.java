package com.bertolini.adapters.respositories.excel.style;

import com.bertolini.adapters.respositories.excel.structure.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

public final class ExcelLayout {

    private ExcelLayout() {}

    public static final String SHEET_TITLE   = "Cash Flow";
    public static final String INFLOW_LABEL  = "Inflow";
    public static final String OUTFLOW_LABEL = "Outflow";
    public static final String NETCASH_LABEL = "Net Cash";

    // Columns - define order in the sheet
    // {fieldKey, label, width}
    public static final String[][] HEADER_COLUMNS = {
            {"id",              "Id"},
            {"bank",            "Bank"},
            {"date",            "Date"},
            {"time",            "Time"},
            {"description",     "Description"},
            {"transactionType", "Type"},
            {"amount",          "Amount"},
    };

    public static final int[] COLUMNS_WIDTH = {
    //         1  2    3    4    5           6    7      8    9     10      11
    //      null;id;bank;date;time;description;type;amount;null;inflow;outflow
            5,   5, 12,  12,  12,  35,         10,  15,    5,   12,    12
    };

    public static final Cell TITLE           = new Cell(0, 1);
    public static final Cell HEADER          = new Cell(2, 1);
    public static final Cell INFLOW          = new Cell(2, 9);
    public static final Cell OUTFLOW         = new Cell(2, 10);
    public static final Cell NET_CASH        = new Cell(6, 9);
    public static final Cell START_DATA_FILL = new Cell(3, 1);
    public static final int ZOOM_LEVEL       = 160;

    public static final CellRangeAddress TITLE_RANGE           = new CellRangeAddress(TITLE.row(), TITLE.row(), TITLE.column(), HEADER_COLUMNS.length);
    public static final CellRangeAddress NET_CASH_TITLE_RANGE  = new CellRangeAddress(NET_CASH.row(), NET_CASH.row(), NET_CASH.column(), NET_CASH.column() + 1);
    public static final CellRangeAddress NET_CASH_AMOUNT_RANGE = new CellRangeAddress(NET_CASH.row() + 1, NET_CASH.row() + 1, NET_CASH.column(), NET_CASH.column() + 1);

    // Merge regions {firstRow, lastRow, firstCol, lastCol}
    public static final CellRangeAddress[] MERGE_REGIONS = new CellRangeAddress[]{
            TITLE_RANGE,
            NET_CASH_TITLE_RANGE,
            NET_CASH_AMOUNT_RANGE
    };

    // Border cells {row, col}
    public static final Cell[] BORDER_CELLS = new Cell[]{
            new Cell(INFLOW.row() + 1, INFLOW.column()),
            new Cell(OUTFLOW.row() + 1, OUTFLOW.column()),
            new Cell(NET_CASH.row() + 1, NET_CASH.column()),
            new Cell(NET_CASH.row() + 1, NET_CASH.column() + 1)
    };
}

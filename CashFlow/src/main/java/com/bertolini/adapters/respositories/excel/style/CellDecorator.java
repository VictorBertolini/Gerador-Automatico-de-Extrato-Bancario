package com.bertolini.adapters.respositories.excel.style;

import lombok.Getter;
import org.apache.poi.ss.usermodel.*;

@Getter
public class CellDecorator {
    private final Workbook workbook;
    private final ExcelTheme theme;

    public CellDecorator(Workbook workbook, ExcelTheme theme) {
        this.workbook = workbook;
        this.theme    = theme;
    }

    public CellStyle titleStyle() {
        return buildStyle(
                theme.getTitleFont(),
                theme.getTitleFontSize(),
                theme.getTitleFontColor(),
                theme.getTitleBgColor(),
                theme.isBordersOnTitle()
        );
    }

    public CellStyle headerStyle() {
        return buildStyle(
                theme.getHeaderFont(),
                theme.getHeaderFontSize(),
                theme.getHeaderFontColor(),
                theme.getHeaderBgColor(),
                true
        );
    }

    public CellStyle resultCellsStyle(IndexedColors bgColor) {
        return buildStyle(
                theme.getHeaderFont(),
                theme.getHeaderFontSize(),
                theme.getHeaderFontColor(),
                bgColor,
                false
        );
    }

    public CellStyle allBordersStyle() {
        CellStyle style = workbook.createCellStyle();
        applyBorders(style);
        return style;
    }

    private CellStyle buildStyle(String fontName, short fontSize,
                                 IndexedColors fontColor, IndexedColors bgColor,
                                 boolean withBorder) {
        CellStyle style = workbook.createCellStyle();

        Font font = workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints(fontSize);
        font.setBold(true);
        font.setColor(fontColor.getIndex());
        style.setFont(font);

        style.setFillForegroundColor(bgColor.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);

        if (withBorder) applyBorders(style);

        return style;
    }

    private void applyBorders(CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }
}

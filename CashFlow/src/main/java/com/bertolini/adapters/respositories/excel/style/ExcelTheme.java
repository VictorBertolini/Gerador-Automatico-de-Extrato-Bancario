package com.bertolini.adapters.respositories.excel.style;

import lombok.Getter;
import org.apache.poi.ss.usermodel.IndexedColors;

@Getter
public class ExcelTheme {
    private final IndexedColors titleBgColor;
    private final IndexedColors titleFontColor;
    private final short titleFontSize;
    private final String titleFont;
    private final IndexedColors headerBgColor;
    private final IndexedColors headerFontColor;
    private final short headerFontSize;
    private final String headerFont;
    private final IndexedColors inflowColor;
    private final IndexedColors outflowColor;
    private final IndexedColors inflowFontColor;
    private final IndexedColors outflowFontColor;
    private final IndexedColors netcashFontColor;
    private final boolean bordersOnTitle;

    private ExcelTheme(Builder builder) {
        this.titleBgColor     = builder.titleBgColor;
        this.titleFontColor   = builder.titleFontColor;
        this.titleFontSize    = builder.titleFontSize;
        this.titleFont        = builder.titleFont;
        this.headerBgColor    = builder.headerBgColor;
        this.headerFontColor  = builder.headerFontColor;
        this.headerFontSize   = builder.headerFontSize;
        this.headerFont       = builder.headerFont;
        this.inflowColor      = builder.inflowColor;
        this.outflowColor     = builder.outflowColor;
        this.inflowFontColor  = builder.inflowFontColor;
        this.outflowFontColor = builder.outflowColor;
        this.netcashFontColor = builder.netcashFontColor;
        this.bordersOnTitle   = builder.bordersOnTitle;
    }

    public static class Builder {
        private IndexedColors titleBgColor     = IndexedColors.DARK_BLUE;
        private IndexedColors titleFontColor   = IndexedColors.WHITE;
        private short titleFontSize            = 26;
        private String titleFont               = "Calibri";
        private IndexedColors headerBgColor    = IndexedColors.DARK_BLUE;
        private IndexedColors headerFontColor  = IndexedColors.WHITE;
        private IndexedColors inflowFontColor  = IndexedColors.WHITE;
        private IndexedColors outflowFontColor = IndexedColors.WHITE;
        private IndexedColors netcashFontColor = IndexedColors.WHITE;
        private short headerFontSize           = 12;
        private String headerFont              = "Calibri";
        private IndexedColors inflowColor      = IndexedColors.DARK_GREEN;
        private IndexedColors outflowColor     = IndexedColors.DARK_RED;
        private boolean bordersOnTitle         = true;

        public Builder titleBgColor(IndexedColors v)     { titleBgColor = v;     return this; }
        public Builder titleFontColor(IndexedColors v)   { titleFontColor = v;   return this; }
        public Builder titleFontSize(short v)            { titleFontSize = v;    return this; }
        public Builder titleFont(String v)               { titleFont = v;        return this; }
        public Builder headerBgColor(IndexedColors v)    { headerBgColor = v;    return this; }
        public Builder headerFontColor(IndexedColors v)  { headerFontColor = v;  return this; }
        public Builder inflowFontColor(IndexedColors v)  { inflowColor = v;      return this; }
        public Builder outflowFontColor(IndexedColors v) { outflowColor = v;     return this; }
        public Builder netcashFontColor(IndexedColors v) { netcashFontColor = v; return this; }
        public Builder headerFontSize(short v)           { headerFontSize = v;   return this; }
        public Builder headerFont(String v)              { headerFont = v;       return this; }
        public Builder inflowColor(IndexedColors v)      { inflowColor = v;      return this; }
        public Builder outflowColor(IndexedColors v)     { outflowColor = v;     return this; }
        public Builder bordersOnTitle(boolean v)         { bordersOnTitle = v;   return this; }

        public ExcelTheme build() { return new ExcelTheme(this); }

        public static ExcelTheme defaultTheme() {
            return new Builder().build();
        }
    }
}

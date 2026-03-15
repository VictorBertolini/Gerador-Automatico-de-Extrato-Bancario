package com.bertolini.adapters.respositories.excel.structure;

public record CellRange(
        int xInit,
        int yInit,
        int xFinal,
        int yFinal
) {
}

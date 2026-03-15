package com.bertolini.adapters.respositories.excel;

import com.bertolini.adapters.respositories.excel.service.LastExcelRowGetter;
import com.bertolini.adapters.respositories.excel.structure.Cell;
import com.bertolini.adapters.respositories.excel.style.ExcelLayout;
import com.bertolini.adapters.respositories.excel.style.ExcelTheme;
import com.bertolini.adapters.respositories.excel.style.ExcelSheetStyler;
import com.bertolini.adapters.respositories.excel.style.ExcelTransactionWriter;
import com.bertolini.core.domain.entitys.TransactionBatch;
import com.bertolini.core.useCases.repository.TransactionRepository;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExcelRepository implements TransactionRepository {
    private final String fileName;
    private final String path = "data\\";
    private final ExcelTheme theme;
    private Workbook workbook;
    private ExcelTransactionWriter excelWriter;
    private LastExcelRowGetter lastExcelRowGetter = new LastExcelRowGetter();

    public ExcelRepository(String fileName, ExcelTheme theme) {
        this.fileName = fileName;
        this.theme    = theme;
        initiateWorkbook();
    }

    @Override
    public void save(TransactionBatch transactionBatch) {
        Sheet sheet = initiateSheet(transactionBatch.getLabel());
        excelWriter = new ExcelTransactionWriter(sheet);
        excelWriter.write(transactionBatch.getTransactionSet());
        saveWorkbook();
    }

    @Override
    public void saveAll(List<TransactionBatch> transactionBatches) {
        for (TransactionBatch batch : transactionBatches) {
            if (batch.getTransactionSet() == null)
                continue;
            save(batch);
        }
        saveWorkbook();
        closeWorkbook();
    }

    @Override
    public Set<String> getIdsByLabel(String label) {
        Sheet sheet = workbook.getSheet(label);
        if (sheet == null) return Set.of();

        Set<String> transactionIds = new HashSet<>();
        int idCol = ExcelLayout.START_DATA_FILL.column();
        int startRow = ExcelLayout.START_DATA_FILL.row();
        Cell limit = lastExcelRowGetter.getLastRowNumber(sheet);

        for (int i = startRow; i < limit.row(); i++) {
            Row row = sheet.getRow(i);
            if (row == null)
                break;

            org.apache.poi.ss.usermodel.Cell cell = row.getCell(idCol);
            if (cell == null || cell.getCellType() == CellType.BLANK)
                break;

            transactionIds.add(cell.getStringCellValue());
        }
        return transactionIds;
    }

    private void initiateWorkbook() {
        String filePath = path + fileName + ".xlsx";
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                this.workbook = new XSSFWorkbook(fis);
                return;
            } catch (Exception e) {
                throw new RuntimeException("Could not open: " + filePath);
            }
        }
        try {
            this.workbook = new XSSFWorkbook();
        } catch (Exception e) {
            throw new RuntimeException("Could not create a workbook");
        }
    }

    private Sheet initiateSheet(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
            new ExcelSheetStyler(workbook, sheet, theme);
        }
        return sheet;
    }

    private void saveWorkbook() {
        try (FileOutputStream fileOut = new FileOutputStream(path + fileName + ".xlsx")) {
            workbook.write(fileOut);
        } catch (Exception e) {
            throw new RuntimeException("Error in saving: " + e.getMessage());
        }
    }

    private void closeWorkbook() {
        try {
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException("Error in closing: " + e.getMessage());
        }
    }
}

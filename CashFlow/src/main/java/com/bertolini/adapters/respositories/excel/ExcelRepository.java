package com.bertolini.adapters.respositories.excel;

import com.bertolini.adapters.respositories.excel.style.ExcelTheme;
import com.bertolini.adapters.respositories.excel.style.ExcelSheetStyler;
import com.bertolini.adapters.respositories.excel.style.ExcelTransactionWriter;
import com.bertolini.core.domain.entitys.TransactionBatch;
import com.bertolini.core.useCases.repository.TransactionRepository;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

public class ExcelRepository implements TransactionRepository {
    private final String fileName;
    private final String path = "data\\";
    private final ExcelTheme theme;
    private Workbook workbook;
    private ExcelTransactionWriter excelWriter;

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
    }

    @Override
    public List<TransactionBatch> getAll() {
        return List.of(); // implementar na próxima etapa
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
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException("Error in saving: " + e.getMessage());
        }
    }
}

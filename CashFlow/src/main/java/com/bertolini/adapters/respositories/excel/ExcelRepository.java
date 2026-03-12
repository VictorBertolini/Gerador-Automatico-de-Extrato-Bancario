package com.bertolini.adapters.respositories.excel;

import com.bertolini.core.domain.entitys.TransactionBatch;
import com.bertolini.core.useCases.repository.TransactionRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelRepository implements TransactionRepository {
    String fileName;
    private final String path = "data\\";
    private Workbook workbook;
    private Sheet sheet;
    private ExcelTransactionWriter excelWriter;

    public ExcelRepository(String fileName) {
        this.fileName = fileName;

        initiateWorkbook();
    }

    @Override
    public void save(TransactionBatch transactionBatch) {
        initiateSheet(transactionBatch.getLabel());
        initiateWriter();

        excelWriter.write(transactionBatch.getTransactionSet());

        saveWorkbook(fileName);
    }

    @Override
    public void saveAll(List<TransactionBatch> transactionBatches) {
        for (TransactionBatch batch : transactionBatches) {
            if (batch.getTransactionSet() != null) {
                initiateSheet(batch.getLabel());
                initiateWriter();
                excelWriter.write(batch.getTransactionSet());
            }
        }
        saveWorkbook(fileName);
    }

    @Override
    public List<TransactionBatch> getAll() {
        return List.of();
    }

    private void initiateWorkbook() {
        try {
            File file = new File(path + fileName + ".xlsx");
            if (file.exists() && file.isFile()) {
                this.workbook = new XSSFWorkbook(file);
                return;
            }
            this.workbook = new XSSFWorkbook();
        } catch (Exception e) {
            throw new RuntimeException("The xlsx file could not be open or could not be found");
        }
    }

    private void initiateSheet(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
            new ExcelSheetStyler(workbook, sheet);
        }

        this.sheet = sheet;
    }

    private void initiateWriter() {
        excelWriter = new ExcelTransactionWriter(sheet);
    }

    private void saveWorkbook(String fileName) {
        try (FileOutputStream fileOut = new FileOutputStream(path + fileName + ".xlsx")) {
            workbook.write(fileOut);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

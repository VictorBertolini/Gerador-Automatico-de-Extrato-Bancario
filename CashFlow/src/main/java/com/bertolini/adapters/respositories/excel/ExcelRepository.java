package com.bertolini.adapters.respositories.excel;

import com.bertolini.core.domain.entitys.TransactionBatch;
import com.bertolini.core.useCases.repository.TransactionRepository;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
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
        String filePath = path + fileName + ".xlsx";
        File file = new File(filePath);

        if (file.exists() && file.isFile()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                this.workbook = new XSSFWorkbook(fis);
                return;
            } catch (Exception e) {
                throw new RuntimeException("Não foi possível abrir: " + filePath);
            }
        }

        try {
            this.workbook = new XSSFWorkbook();
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível criar workbook");
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
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

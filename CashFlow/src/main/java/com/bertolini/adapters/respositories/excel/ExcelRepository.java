package com.bertolini.adapters.respositories.excel;

import com.bertolini.core.domain.entitys.TransactionBatch;
import com.bertolini.core.useCases.repository.TransactionRepository;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

public class ExcelRepository implements TransactionRepository {
    String fileName;
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

    private void initiateWorkbook() {
        this.workbook = new XSSFWorkbook();
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
        try (FileOutputStream fileOut = new FileOutputStream(fileName + ".xlsx")) {
            workbook.write(fileOut);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

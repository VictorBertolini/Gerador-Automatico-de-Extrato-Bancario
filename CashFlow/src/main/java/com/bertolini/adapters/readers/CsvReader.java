package com.bertolini.adapters.readers;

import com.bertolini.core.useCases.reader.BankStatementReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvReader implements BankStatementReader {

    @Override
    public ArrayList<String> readTransactions(String fileName, boolean header){
        String pathLocation = "data/";
        Path path = Paths.get(pathLocation + fileName);
        List<String> transactionData;

        try {
            transactionData = Files.readAllLines(path);
        } catch (Exception e) {
            throw new RuntimeException("Error reading file " + pathLocation + fileName + ".csv");
        }

        if (header)
            transactionData.removeFirst();

        return new  ArrayList<>(transactionData);
    }
}
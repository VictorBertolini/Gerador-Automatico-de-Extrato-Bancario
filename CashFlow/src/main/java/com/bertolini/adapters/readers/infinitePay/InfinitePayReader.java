package com.bertolini.adapters.readers.infinitePay;

import com.bertolini.core.useCases.reader.BankStatementReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InfinitePayReader implements BankStatementReader {
    private String pathLocation = "data/";

    @Override
    public ArrayList<String> readTransactions(String fileName){
        Path path = Paths.get(pathLocation + fileName);
        List<String> transactionData = new ArrayList<>();

        try {
            transactionData = Files.readAllLines(path);
        } catch (IOException e) {
            System.err.println("Error reading file " + pathLocation + fileName + ".csv");
            e.printStackTrace();
        }

        transactionData.removeFirst(); // Remove header

        return new  ArrayList<>(transactionData);
    }
}
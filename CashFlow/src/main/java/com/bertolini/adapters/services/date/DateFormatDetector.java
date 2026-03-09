package com.bertolini.adapters.services.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DateFormatDetector {

    private static final List<DateTimeFormatter> SUPPORTED_FORMATS = List.of(
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy"),
            DateTimeFormatter.ofPattern("MM-dd-yyyy")
    );

    public DateTimeFormatter detect(String sampleDate) {
        for (DateTimeFormatter formatter : SUPPORTED_FORMATS) {
            try {
                LocalDate.parse(sampleDate.trim(), formatter);
                return formatter;
            } catch (Exception e) {

            }
        }
        throw new RuntimeException("Data format " + sampleDate + " not allowed!");
    }
}

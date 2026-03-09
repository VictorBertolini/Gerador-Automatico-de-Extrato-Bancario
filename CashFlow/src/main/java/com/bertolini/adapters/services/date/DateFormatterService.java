package com.bertolini.adapters.services.date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatterService {
    private final DateFormatDetector dateFormatDetector;
    private DateTimeFormatter format;

    public DateFormatterService(DateFormatDetector dateFormatDetector) {
        this.dateFormatDetector = dateFormatDetector;
    }

    private void getFormatter(String sampleDate) {
        format = dateFormatDetector.detect(sampleDate);
    }

    public LocalDate getFormattedDate(String date) {
        date = date.trim();
        if (format == null)
            getFormatter(date);

        return LocalDate.parse(date, format);
    }


}

package com.shilaeva.logger;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class DateMessageFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        String date = DateTimeFormatter.ISO_INSTANT.format(new Date().toInstant());

        return String.format("%s: %s\n", date, record.getMessage());
    }
}

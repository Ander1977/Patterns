package ru.netology;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateUtils {
    private static final String DATE__FORMAT = "dd.MM.yyyy";
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE__FORMAT);

    public String localDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(7);
        return localDateTime.format(dateFormat);
    }
}

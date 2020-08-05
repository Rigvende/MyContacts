package com.itechart.contacts.domain.util;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Class for converting date types (sql date and local date)
 * @author Marianna Patrusova
 * @version 1.0
 */
public class DateConverter {

    private DateConverter() {}

    public static Date convertToSqlDate(LocalDate date) {
        return (date != null) ? Date.valueOf(date) : null;
    }

    public static LocalDate convertToLocalDate(Date date) {
        return (date != null) ? date.toLocalDate() : null;
    }

}

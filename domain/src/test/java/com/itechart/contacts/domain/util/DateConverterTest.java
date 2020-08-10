package com.itechart.contacts.domain.util;

import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class DateConverterTest {

    @Test
    void convertToSqlDate() {
        Date date = DateConverter.convertToSqlDate(LocalDate.now());
        assertNotNull(date);
    }

    @Test
    void convertToLocalDate() {
        LocalDate date = DateConverter.convertToLocalDate(new Date(System.currentTimeMillis()));
        assertNotNull(date);
    }
}
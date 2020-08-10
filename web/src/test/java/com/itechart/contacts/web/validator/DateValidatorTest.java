package com.itechart.contacts.web.validator;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class DateValidatorTest {

    @Test
    void isValidDate() {
        LocalDate date1 = LocalDate.of(LocalDate.now().getYear() + 1, 1, 1);
        LocalDate date2 = LocalDate.of(LocalDate.now().getYear() - 101, 1, 1);
        LocalDate date3 = LocalDate.of(LocalDate.now().getYear() - 50, 1, 1);
        assertFalse(DateValidator.isValidDate(date1));
        assertFalse(DateValidator.isValidDate(date2));
        assertTrue(DateValidator.isValidDate(date3));
    }
}
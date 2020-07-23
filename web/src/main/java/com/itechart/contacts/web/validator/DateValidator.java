package com.itechart.contacts.web.validator;

import java.time.LocalDate;

/**
 * Class for validating user's date input.
 * @author Marianna Patrusova
 * @version 1.0
 */
public class DateValidator {

    private DateValidator() {}

    //birthday
    public static boolean isValidDate(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        return date.compareTo(currentDate) <= 0;
    }

}

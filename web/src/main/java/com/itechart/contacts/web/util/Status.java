package com.itechart.contacts.web.util;

/**
 * Enum for contact data changing flags.
 * @author Marianna Patrusova
 * @version 1.0
 */
public enum Status {

    UPDATED ("updated"),
    DELETED ("deleted");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getValue() {
        return status;
    }

}

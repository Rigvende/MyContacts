package com.itechart.contacts.web.util;

public enum Status {

    NEW ("new"),
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

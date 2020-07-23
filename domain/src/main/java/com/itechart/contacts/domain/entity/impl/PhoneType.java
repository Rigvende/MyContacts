package com.itechart.contacts.domain.entity.impl;

/**
 * Enum for phone types storage
 * @author Marianna Patrusova
 * @version 1.0
 */
public enum PhoneType {

    HOME("домашний"),
    WORK("рабочий"),
    MOBILE("мобильный"),
    OTHER("иной");

    private final String phoneType;

    PhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getValue() {
        return phoneType;
    }

    public static PhoneType getPhoneType(String value) {
        switch(value) {
            case "домашний":
                return PhoneType.HOME;
            case "рабочий":
                return PhoneType.WORK;
            case "мобильный":
                return PhoneType.MOBILE;
            case "иной":
                return PhoneType.OTHER;
            default:
                return null;
        }
    }

}

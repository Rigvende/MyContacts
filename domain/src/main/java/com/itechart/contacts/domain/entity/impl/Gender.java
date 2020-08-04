package com.itechart.contacts.domain.entity.impl;

/**
 * Enum for gender types storage
 * @author Marianna Patrusova
 * @version 1.0
 */
public enum Gender {

    UNKNOWN("unknown"),
    MALE("male"),
    FEMALE("female");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getValue() {
        return gender;
    }

    public static Gender getGender(String value) {
        switch(value) {
            case "unknown":
                return Gender.UNKNOWN;
            case "male":
                return Gender.MALE;
            case "female":
                return Gender.FEMALE;
            default:
                return null;
        }
    }

}

package com.itechart.contacts.domain.entity.impl;

/**
 * Enum for gender types storage
 * @author Marianna Patrusova
 * @version 1.0
 */
public enum Gender {

    MAN("male"),
    WOMAN("female");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getValue() {
        return gender;
    }

    public static Gender getGender(String value) {
        switch(value) {
            case "male":
                return Gender.MAN;
            case "female":
                return Gender.WOMAN;
            default:
                return null;
        }
    }

}

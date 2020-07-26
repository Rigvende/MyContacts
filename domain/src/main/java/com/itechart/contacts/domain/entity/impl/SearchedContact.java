package com.itechart.contacts.domain.entity.impl;

import com.itechart.contacts.domain.entity.AbstractEntity;

import java.util.Objects;

public class SearchedContact extends AbstractEntity {

    private static final long serialVersionUID = -3307283967050129689L;
    private String name;
    private String surname;
    private String patronymic;
    private String birthday;
    private String condition;
    private String gender;
    private String citizenship;
    private String city;
    private String country;
    private String street;

    public SearchedContact(String name, String surname, String patronymic,
                           String birthday, String gender, String citizenship,
                           String condition, String city, String country, String street) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.gender = gender;
        this.citizenship = citizenship;
        this.condition = condition;
        this.city = city;
        this.country = country;
        this.street = street;
    }
    public SearchedContact(AbstractEntity entity) {
        Contact contact = (Contact) entity;
        this.name = contact.getName();
        this.surname = contact.getSurname();
        this.patronymic = contact.getPatronymic();
        this.birthday = contact.getStringBirthday();
        this.gender = contact.getStringGender();
        this.citizenship = contact.getCitizenship();
        this.condition = "";
        this.city = contact.getCity();
        this.country = contact.getCountry();
        this.street = contact.getAddress();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getPatronymic() {
        return patronymic;
    }
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getCitizenship() {
        return citizenship;
    }
    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SearchedContact that = (SearchedContact) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(patronymic, that.patronymic) &&
                Objects.equals(birthday, that.birthday) &&
                Objects.equals(condition, that.condition) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(citizenship, that.citizenship) &&
                Objects.equals(city, that.city) &&
                Objects.equals(country, that.country) &&
                Objects.equals(street, that.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, patronymic, birthday, gender,
                            citizenship, condition, city, country, street);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("SearchedContact{");
        builder.append("name='").append(name).append('\'').append(", surname='").append(surname)
                .append('\'').append(", patronymic='").append(patronymic).append('\'')
                .append(", birthday='").append(birthday).append('\'').append(", condition='")
                .append(condition).append('\'').append(", gender='").append(gender)
                .append('\'').append(", citizenship='").append(citizenship).append('\'')
                .append(", city='").append(city).append('\'').append(", country='").append(country)
                .append('\'').append(", street='").append(street).append('\'').append('}');
        return builder.toString();
    }

}

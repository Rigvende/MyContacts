package com.itechart.contacts.domain.entity.impl;

import com.itechart.contacts.domain.entity.AbstractEntity;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Class of entity-type for keeping contact data
 * @author Marianna Patrusova
 * @version 1.0
 */
public class Contact extends AbstractEntity {

    private static final long serialVersionUID = -6840416141108365787L;
    private long contactId;
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate birthday;
    private Gender gender;
    private String citizenship;
    private String familyStatus;
    private String website;
    private String email;
    private String work;
    private String city;
    private String country;
    private String address;
    private String zipcode;
    private long photoId;
    private Timestamp deleted;

    public Contact() {
    }
    public Contact(long contactId, String name, String surname, String patronymic,
                   LocalDate birthday, Gender gender, String citizenship, String familyStatus,
                   String website, String email, String work, String country, String city,
                   String address, String zipcode, long photoId, Timestamp deleted) {
        this.contactId = contactId;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.gender = gender;
        this.citizenship = citizenship;
        this.familyStatus = familyStatus;
        this.website = website;
        this.email = email;
        this.work = work;
        this.country = country;
        this.city = city;
        this.address = address;
        this.zipcode = zipcode;
        this.photoId = photoId;
        this.deleted = deleted;
    }

    public long getContactId() {
        return contactId;
    }
    public void setContactId(long contactId) {
        this.contactId = contactId;
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
    public LocalDate getBirthday() {
        return birthday;
    }
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public String getCitizenship() {
        return citizenship;
    }
    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }
    public String getFamilyStatus() {
        return familyStatus;
    }
    public void setFamilyStatus(String familyStatus) {
        this.familyStatus = familyStatus;
    }
    public String getWebsite() {
        return website;
    }
    public void setWebsite(String website) {
        this.website = website;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getWork() {
        return work;
    }
    public void setWork(String work) {
        this.work = work;
    }
    public long getPhotoId() {
        return photoId;
    }
    public void setPhotoId(long photoId) {
        this.photoId = photoId;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getZipcode() {
        return zipcode;
    }
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    public Timestamp getDeleted() {
        return deleted;
    }
    public void setDeleted(Timestamp deleted) {
        this.deleted = deleted;
    }
    public String getStringBirthday() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return birthday.format(fmt);
    }
    public String getStringGender() {
        return gender.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return contactId == contact.contactId
                && photoId == contact.photoId
                && deleted == contact.deleted
                && Objects.equals(name, contact.name)
                && Objects.equals(surname, contact.surname)
                && Objects.equals(patronymic, contact.patronymic)
                && Objects.equals(birthday, contact.birthday)
                && gender == contact.gender
                && Objects.equals(citizenship, contact.citizenship)
                && Objects.equals(familyStatus, contact.familyStatus)
                && Objects.equals(website, contact.website)
                && Objects.equals(email, contact.email)
                && Objects.equals(country, contact.country)
                && Objects.equals(city, contact.city)
                && Objects.equals(address, contact.address)
                && Objects.equals(zipcode, contact.zipcode)
                && Objects.equals(work, contact.work);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int)(prime * result + contactId + photoId);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        result = prime * result + ((patronymic == null) ? 0 : patronymic.hashCode());
        result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((citizenship == null) ? 0 : citizenship.hashCode());
        result = prime * result + ((familyStatus == null) ? 0 : familyStatus.hashCode());
        result = prime * result + ((website == null) ? 0 : website.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((work == null) ? 0 : work.hashCode());
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((zipcode == null) ? 0 : zipcode.hashCode());
        result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Contact{");
        stringBuilder.append("contactId=").append(contactId).append(", name='")
                .append(name).append('\'').append(", surname='").append(surname)
                .append('\'').append(", patronymic='").append(patronymic).append('\'')
                .append(", birthday=").append(birthday).append(", gender=")
                .append(gender.getValue()).append(", citizenship='").append(citizenship)
                .append('\'').append(", familyStatus='").append(familyStatus)
                .append('\'').append(", website='").append(website).append('\'')
                .append(", email='").append(email).append('\'').append(", work='")
                .append(work).append('\'').append(", country='").append(country)
                .append('\'').append(", city='").append(city).append('\'')
                .append(", address='").append(address).append('\'').append(", zipcode='")
                .append(zipcode).append('\'').append(", photoId=").append(photoId)
                .append(", deleted=").append(deleted).append('}');
        return stringBuilder.toString();
    }

}

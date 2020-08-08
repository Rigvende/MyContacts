package com.itechart.contacts.web.util;

import com.itechart.contacts.domain.entity.impl.*;
import com.itechart.contacts.web.validator.DateValidator;
import com.itechart.contacts.web.validator.StringValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Class for parsing request data and creating entities using this data.
 *
 * @author Marianna Patrusova
 * @version 1.0
 */
public class RequestParser {

    private final static Logger LOGGER = LogManager.getLogger();

    private RequestParser() {
    }

    public static Attachment createAttachment(HttpServletRequest request) {
        String name = request.getParameter("attachment");
        String path = "C:\\attachments\\" + name + "_" + new Timestamp(System.currentTimeMillis());
        LocalDate date = LocalDate.now();
        String comments = request.getParameter("comments");
        long contactId = Long.parseLong(request.getParameter("contactId")); //сначала создать контакт, потом получить его айди, потом передать в аттачмент
        return new Attachment(0L, path, name, date, comments, contactId);
    }

    public static Contact createContact(HttpServletRequest request) {
        String contactId = request.getParameter("id");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String patronymic = request.getParameter("patronymic");
        String requestBirthday = request.getParameter("birthday");
        String requestGender = request.getParameter("gender");
        String citizenship = request.getParameter("citizenship");
        String familyStatus = request.getParameter("status");
        String website = request.getParameter("website");
        String email = request.getParameter("email");
        String work = request.getParameter("work");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String location = request.getParameter("location");
        String zipcode = request.getParameter("zipcode");
        String requestPhotoId = request.getParameter("photoId");
        if (validateContact(contactId, name, surname, patronymic, requestBirthday, requestGender, citizenship,
                            familyStatus, website, email, work, country, city, location, zipcode, requestPhotoId)) {
            long id;
            if (contactId != null && !contactId.isEmpty()) {
                id = Long.parseLong(contactId);
            } else {
                id = 0L;
            }
            LocalDate birthday;
            if (requestBirthday != null && !requestBirthday.isEmpty()
                    && DateValidator.isValidDate(LocalDate.parse(requestBirthday))) {
                birthday = LocalDate.parse(requestBirthday);
            } else {
                birthday = null;
            }
            Gender gender = Gender.getGender(requestGender);
            long photoId;
            if (requestPhotoId != null && !requestPhotoId.isEmpty()) {
                photoId = Long.parseLong(requestPhotoId);
            } else {
                photoId = 0L;
            }
            return new Contact(id, name, surname, patronymic, birthday, gender,
                    citizenship, familyStatus, website, email, work,
                    country, city, location, zipcode, photoId, null);
        }
        return null;
    }

    private static boolean validateContact(String id, String name, String surname, String patronymic,
                                           String birthday, String gender, String citizenship, String familyStatus,
                                           String website, String email, String work, String country,
                                           String city, String location, String zipcode, String photoId) {
        return StringValidator.isValidId(id) && StringValidator.isValidName(name)
                && StringValidator.isValidName(surname) && StringValidator.isValidPatronymic(patronymic)
                && StringValidator.isValidDate(birthday) && StringValidator.isValidGender(gender)
                && StringValidator.isValidData(citizenship) && StringValidator.isValidData(familyStatus)
                && StringValidator.isValidWebsite(website) && StringValidator.isValidEmail(email)
                && StringValidator.isValidData(work) && StringValidator.isValidData(country)
                && StringValidator.isValidData(city) && StringValidator.isValidData(location)
                && StringValidator.isValidZipcode(zipcode) && StringValidator.isValidId(photoId);
    }

    public static Phone createPhone(HttpServletRequest request) {
        String countryCode = request.getParameter("p_code");
        String operatorCode = request.getParameter("p_operator");
        String number = request.getParameter("number");
        String comments = request.getParameter("p_comments");
        PhoneType type = PhoneType.getPhoneType(request.getParameter("p_type"));
        long contactId = Long.parseLong(request.getParameter("contactId")); //сначала создать контакт, потом получить его айди, потом передать в телефон
        return new Phone(0L, countryCode, operatorCode, number, type, comments, contactId);
    }

    public static Photo createPhoto(HttpServletRequest request) {
        String name = request.getParameter("photo_name");
        if (name != null) {
            name = name.substring(name.lastIndexOf("\\") + 1);
        } else {
            name = "";
        }
        long id = 0L;
        String path = "";
        if (request.getParameter("photoId") != null
                && !request.getParameter("photoId").isEmpty()) {
            id = Long.parseLong(request.getParameter("photoId"));
            path = request.getParameter("photo_path");
        }
        return new Photo(id, path, name);
    }

}

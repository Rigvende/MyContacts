package com.itechart.contacts.web.util;

import com.itechart.contacts.domain.entity.impl.*;
import com.itechart.contacts.web.validator.DateValidator;
import com.itechart.contacts.web.validator.StringValidator;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for parsing request data and creating entities using this data.
 * @author Marianna Patrusova
 * @version 1.0
 */
public class RequestParser {

    private RequestParser() {
    }

    public static Attachment createAttachment(HttpServletRequest request) {
        String attachmentId = request.getParameter("attachmentId");
        String attachmentStatus = request.getParameter("attachmentStatus");
        String comment = request.getParameter("attachmentComment");
        String attachmentPath = request.getParameter("attachmentPath");
        String attachmentName = request.getParameter("attachmentName");
        String loadDate = request.getParameter("loadDate");
        String contactId = request.getParameter("id");
        if (validateAttachment(attachmentId, attachmentName, attachmentPath,
                               attachmentStatus, comment, loadDate, contactId)) {
            if (attachmentName != null && !attachmentName.isEmpty()) {
                attachmentName = attachmentName.substring(attachmentName.lastIndexOf("\\") + 1);
            } else {
                attachmentName = "";
            }
            long id = 0L;
            long idContact = 0L;
            String path = "";
            if (attachmentId != null && !attachmentId.isEmpty()) {
                id = Long.parseLong(attachmentId);
                path = attachmentPath;
            }
            if (contactId != null && contactId.isEmpty()) {
                idContact = Long.parseLong(contactId);
            }
            LocalDate date;
            if (loadDate != null && !loadDate.isEmpty()
                    && DateValidator.isValidDate(LocalDate.parse(loadDate))) {
                date = LocalDate.parse(loadDate);
            } else {
                date = null;
            }
            return new Attachment(id, path, attachmentName, date, comment, idContact);
        } else {
            return null;
        }
    }

    private static boolean validateAttachment(String id, String name, String path, String status,
                                              String comment, String date, String idContact) {
        return StringValidator.isValidId(id) && StringValidator.isValidFileName(name)
                && StringValidator.isValidFilePath(path) && StringValidator.isValidStatus(status)
                && StringValidator.isValidComment(comment) && StringValidator.isValidDate(date)
                && StringValidator.isValidId(idContact);
    }

    public static Contact createContact(Map<String, String> request) {
        String contactId = request.get("idContact");
        String name = request.get("contactName");
        String surname = request.get("surname");
        String patronymic = request.get("patronymic");
        String requestBirthday = request.get("birthday");
        String requestGender = request.get("gender");
        String citizenship = request.get("citizenship");
        String familyStatus = request.get("status");
        String website = request.get("website");
        String email = request.get("email");
        String work = request.get("work");
        String country = request.get("country");
        String city = request.get("city");
        String location = request.get("address");
        String zipcode = request.get("zipcode");
        String requestPhotoId = request.get("idPhoto");
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

//    public static Contact createContact(HttpServletRequest request) {
//        String contactId = request.getParameter("id");
//        String name = request.getParameter("name");
//        String surname = request.getParameter("surname");
//        String patronymic = request.getParameter("patronymic");
//        String requestBirthday = request.getParameter("birthday");
//        String requestGender = request.getParameter("gender");
//        String citizenship = request.getParameter("citizenship");
//        String familyStatus = request.getParameter("status");
//        String website = request.getParameter("website");
//        String email = request.getParameter("email");
//        String work = request.getParameter("work");
//        String country = request.getParameter("country");
//        String city = request.getParameter("city");
//        String location = request.getParameter("location");
//        String zipcode = request.getParameter("zipcode");
//        String requestPhotoId = request.getParameter("photoId");
//        if (validateContact(contactId, name, surname, patronymic, requestBirthday, requestGender, citizenship,
//                            familyStatus, website, email, work, country, city, location, zipcode, requestPhotoId)) {
//            long id;
//            if (contactId != null && !contactId.isEmpty()) {
//                id = Long.parseLong(contactId);
//            } else {
//                id = 0L;
//            }
//            LocalDate birthday;
//            if (requestBirthday != null && !requestBirthday.isEmpty()
//                    && DateValidator.isValidDate(LocalDate.parse(requestBirthday))) {
//                birthday = LocalDate.parse(requestBirthday);
//            } else {
//                birthday = null;
//            }
//            Gender gender = Gender.getGender(requestGender);
//            long photoId;
//            if (requestPhotoId != null && !requestPhotoId.isEmpty()) {
//                photoId = Long.parseLong(requestPhotoId);
//            } else {
//                photoId = 0L;
//            }
//            return new Contact(id, name, surname, patronymic, birthday, gender,
//                    citizenship, familyStatus, website, email, work,
//                    country, city, location, zipcode, photoId, null);
//        }
//        return null;
//    }

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
        String phoneId = request.getParameter("phoneId");
        String countryCode = request.getParameter("p_code");
        String operatorCode = request.getParameter("p_operator");
        String number = request.getParameter("number");
        String comments = request.getParameter("p_comments");
        String phoneType = request.getParameter("p_type");
        String contactId = request.getParameter("contactId");
        String phoneStatus = request.getParameter("phoneStatus");
        if (validatePhone(phoneId, countryCode, operatorCode, number,
                          comments, phoneType, contactId, phoneStatus)) {
            PhoneType type = PhoneType.getPhoneType(phoneType);
            long id;
            if (contactId != null && contactId.isEmpty()) {
                id = Long.parseLong(phoneId);
            } else {
                id = 0L;
            }
            long idContact;
            if (contactId != null && contactId.isEmpty()) {
                 idContact = Long.parseLong(contactId);
            } else {
                idContact = 0L;
            }
            return new Phone(id, countryCode, operatorCode, number, type, comments, idContact);
        } else {
            return null;
        }
    }

    private static boolean validatePhone(String id, String code, String operator, String number,
                                         String comments, String phoneType, String contactId, String status) {
        return StringValidator.isValidId(id) && StringValidator.isValidCode(code)
                && StringValidator.isValidCode(operator) && StringValidator.isValidPhone(number)
                && StringValidator.isValidComment(comments) && StringValidator.isValidType(phoneType)
                && StringValidator.isValidId(contactId) && StringValidator.isValidStatus(status);
    }

    public static Photo createPhoto(Map<String, String> request) {
        String name = request.get("photo_name");
        String photoId = request.get("idPhoto");
        String photoPath = request.get("photo_path");
        String photoStatus = request.get("photo_status");
        if (validatePhoto(photoId, name, photoPath, photoStatus)) {
            if (name == null) {
                name = "";
            }
            long id = 0L;
            String path = "";
            if (photoId != null && !photoId.isEmpty()) {
                id = Long.parseLong(photoId);
                path = photoPath;
            }
            return new Photo(id, path, name);
        } else {
            return null;
        }
    }

//    public static Photo createPhoto(HttpServletRequest request) {
//        String name = request.getParameter("photo_name");
//        String photoId = request.getParameter("photoId");
//        String photoPath = request.getParameter("photo_path");
//        String photoStatus = request.getParameter("photo_status");
//        if (validatePhoto(photoId, name, photoPath, photoStatus)) {
//            if (name != null && !name.isEmpty()) {
//                name = name.substring(name.lastIndexOf("\\") + 1);
//            } else {
//                name = "";
//            }
//            long id = 0L;
//            String path = "";
//            if (photoId != null && !photoId.isEmpty()) {
//                id = Long.parseLong(photoId);
//                path = photoPath;
//            }
//            return new Photo(id, path, name);
//        } else {
//            return null;
//        }
//    }

    private static boolean validatePhoto(String id, String name, String path, String status) {
        return StringValidator.isValidId(id) && StringValidator.isValidFileName(name)
                && StringValidator.isValidPhotoPath(path) && StringValidator.isValidStatus(status);
    }

}

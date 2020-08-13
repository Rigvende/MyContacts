package com.itechart.contacts.web.util;

import com.itechart.contacts.domain.entity.impl.*;
import com.itechart.contacts.web.validator.DateValidator;
import com.itechart.contacts.web.validator.StringValidator;
import org.apache.commons.fileupload.FileItem;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Class for parsing request form data and creating entities using this data.
 * @author Marianna Patrusova
 * @version 1.0
 */
public class RequestEntityBuilder {

    private final static String UTF_8 = "UTF-8";

    private RequestEntityBuilder() {
    }

    //util method for getting each attachment info from form data
    public static void fillAttachments(List<Attachment> attachments, FileItem item, Map<String, String> attachmentParameters,
                                  String id, String name) throws UnsupportedEncodingException {
        switch (item.getFieldName()) {
            case "attachments[][attachmentId]":
                attachmentParameters.put("attachmentId", item.getString(UTF_8));
                break;
            case "attachments[][attachmentComment]":
                attachmentParameters.put("attachmentComment", item.getString(UTF_8));
                break;
            case "attachments[][attachmentPath]":
                attachmentParameters.put("attachmentPath", item.getString(UTF_8));
                break;
            case "attachments[][loadDate]":
                attachmentParameters.put("loadDate", item.getString(UTF_8));
                break;
            case "attachments[][attachmentStatus]":
                attachmentParameters.put("attachmentStatus", item.getString(UTF_8));
                attachmentParameters.put("contactId", id);
                attachmentParameters.put("attachmentName", name);
//                System.out.println(attachmentParameters.get("attachmentId"));
//                System.out.println(attachmentParameters.get("attachmentComment"));
//                System.out.println(attachmentParameters.get("attachmentPath"));
//                System.out.println(attachmentParameters.get("loadDate"));
//                System.out.println(attachmentParameters.get("attachmentStatus"));
//                System.out.println(attachmentParameters.get("contactId"));
//                System.out.println(attachmentParameters.get("attachmentName"));
                Attachment attachment = RequestEntityBuilder.createAttachment(attachmentParameters);
                System.out.println(attachment);
                attachments.add(attachment);
                break;
        }
    }

    public static Attachment createAttachment(Map<String, String> request) {
        String attachmentId = request.get("attachmentId");
        String attachmentStatus = request.get("attachmentStatus");
        String comment = request.get("attachmentComment");
        String contactId = request.get("contactId");
        String attachmentName = request.get("attachmentName");
        String attachmentPath = request.get("attachmentPath");
        String loadDate = request.get("loadDate");
        if (validateAttachment(attachmentId, attachmentName, attachmentStatus, loadDate, comment, contactId)) {
            long id = 0L;
            long idContact = 0L;
            String path = "";
            if (attachmentId != null && !attachmentId.isEmpty()) {
                id = Long.parseLong(attachmentId);
                path = attachmentPath;
            }
            if (contactId != null && !contactId.isEmpty()) {
                idContact = Long.parseLong(contactId);
            }
            LocalDate date;
            if (loadDate != null && !loadDate.isEmpty()
                    && DateValidator.isValidDate(LocalDate.parse(loadDate))) {
                date = LocalDate.parse(loadDate);
            } else {
                date = LocalDate.now();
            }
            return new Attachment(id, path, attachmentName, date, comment, idContact, attachmentStatus);
        } else {
            return null;
        }
    }

    private static boolean validateAttachment(String id, String name, String status,
                                              String date, String comment, String idContact) {
        return StringValidator.isValidId(id) && StringValidator.isValidFileName(name)
                && StringValidator.isValidStatus(status) && StringValidator.isValidComment(comment)
                && StringValidator.isValidDate(date) && StringValidator.isValidId(idContact);
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
            return new Photo(id, path, name, photoStatus);
        } else {
            return null;
        }
    }

    private static boolean validatePhoto(String id, String name, String path, String status) {
        return StringValidator.isValidId(id) && StringValidator.isValidFileName(name)
                && StringValidator.isValidPhotoPath(path) && StringValidator.isValidStatus(status);
    }

    //util method for getting each phone info from form data
    public static void fillPhones(List<Phone> phones, FileItem item, Map<String, String> phoneParameters,
                                  String id) throws UnsupportedEncodingException {
        switch (item.getFieldName()) {
            case "phones[][phoneId]":
                phoneParameters.put("phoneId", item.getString(UTF_8));
                break;
            case "phones[][countryCode]":
                phoneParameters.put("countryCode", item.getString(UTF_8));
                break;
            case "phones[][operatorCode]":
                phoneParameters.put("operatorCode", item.getString(UTF_8));
                break;
            case "phones[][number]":
                phoneParameters.put("number", item.getString(UTF_8));
                break;
            case "phones[][phoneType]":
                phoneParameters.put("phoneType", item.getString(UTF_8));
                break;
            case "phones[][phoneComment]":
                phoneParameters.put("phoneComment", item.getString(UTF_8));
                break;
            case "phones[][phoneStatus]":
                phoneParameters.put("phoneStatus", item.getString(UTF_8));
                phoneParameters.put("contactId", id);
                Phone phone = RequestEntityBuilder.createPhone(phoneParameters);
                phones.add(phone);
                break;
        }
    }

    private static Phone createPhone(Map<String, String> request) {
            String phoneId = request.get("phoneId");
            String countryCode = request.get("countryCode");
            String operatorCode = request.get("operatorCode");
            String number = request.get("number");
            String comments = request.get("phoneComment");
            String phoneType = request.get("phoneType");
            String contactId = request.get("contactId");
            String phoneStatus = request.get("phoneStatus");
            if (validatePhone(phoneId, countryCode, operatorCode, number,
                    comments, phoneType, contactId, phoneStatus)) {
                PhoneType type = PhoneType.getPhoneType(phoneType);
                long id;
                if (phoneId != null && !phoneId.isEmpty()) {
                    id = Long.parseLong(phoneId);
                } else {
                    id = 0L;
                }
                long idContact;
                if (contactId != null && !contactId.isEmpty()) {
                    idContact = Long.parseLong(contactId);
                } else {
                    idContact = 0L;
                }
                return new Phone(id, countryCode, operatorCode, number,
                                 type, comments, idContact, phoneStatus);
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

}

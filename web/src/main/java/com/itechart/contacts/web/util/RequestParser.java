package com.itechart.contacts.web.util;

import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDate;

public class RequestParser {

    private final static Logger LOGGER = LogManager.getLogger();

    private RequestParser() {}

    public static Attachment createAttachment(HttpServletRequest request) {
        String name = request.getParameter("attachment");
        String path = "C:\\attachments\\" + name + "_" + new Timestamp(System.currentTimeMillis());
        LocalDate date = LocalDate.now();
        String comments = request.getParameter("comments");
        long contactId = Long.parseLong(request.getParameter("contactId")); //сначала создать контакт, потом получить его айди, потом передать в аттачмент
        return new Attachment(0L, path, name, date, comments, contactId);
    }

    public static Contact createContact(HttpServletRequest request) {
        long id;
        if (request.getParameter("id") != null
                && !request.getParameter("id").isEmpty()) {
            id = Long.parseLong(request.getParameter("id"));
        } else {
            id = 0L;
        }
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String patronymic = request.getParameter("patronymic");
        LocalDate birthday;
        if (request.getParameter("birthday") != null
                && !request.getParameter("birthday").isEmpty()) {
            birthday = LocalDate.parse(request.getParameter("birthday"));
        } else {
            birthday = null;
        }
        Gender gender = Gender.getGender(request.getParameter("gender"));
        String citizenship = request.getParameter("citizenship");
        String familyStatus = request.getParameter("status");
        String website = request.getParameter("website");
        String email = request.getParameter("email");
        String work = request.getParameter("work");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String location = request.getParameter("location");
        String zipcode = request.getParameter("zipcode");
        long photoId;
        if (request.getParameter("photoId") != null
                && !request.getParameter("photoId").isEmpty()) {
            photoId = Long.parseLong(request.getParameter("photoId"));
        } else {
            photoId = 0L;
        }
        return new Contact(id, name, surname, patronymic, birthday, gender,
                citizenship, familyStatus, website, email, work,
                country, city, location, zipcode, photoId, null);
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
//            path = "../image/photos/" + id + "/";
            path = request.getParameter("photo_path");
        }
        return new Photo(id, path, name);
    }

}

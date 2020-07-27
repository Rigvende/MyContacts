package com.itechart.contacts.domain.entity;

import com.itechart.contacts.domain.entity.impl.*;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.util.DateConverter;
import com.itechart.contacts.domain.util.PathParser;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Class for creating different instances of {@link AbstractEntity}. "Builder" pattern is used
 * @author Marianna Patrusova
 * @version 1.0
 */
public class EntityBuilder {

    private final static Logger LOGGER = LogManager.getLogger();

    public static AbstractEntity createAttachment(ResultSet resultSet) throws DaoException {
        AbstractEntity attachment;
        try {
            long id = resultSet.getLong("id_attachment");
            String path = PathParser.parse(resultSet.getString("attachment_path"));
            String name = resultSet.getString("attachment_name");
            LocalDate date = DateConverter.convertToLocalDate(resultSet.getDate("load_date"));
            String comments = resultSet.getString("comments");
            long contactId = resultSet.getLong("id_contact");
            attachment = new Attachment(id, path, name, date, comments, contactId);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Cannot build attachment. Error has occurred. ", e);
            throw new DaoException(e);
        }
        return attachment;
    }

    public static AbstractEntity createAttachment(HttpServletRequest request) {
        String name = request.getParameter("attachment");
        String path = "C:\\attachments\\" + name + "_" + new Timestamp(System.currentTimeMillis());
        LocalDate date = LocalDate.now();
        String comments = request.getParameter("comments");
        long contactId = Long.parseLong(request.getParameter("contactId")); //сначала создать контакт, потом получить его айди, потом передать в аттачмент
        return new Attachment(0L, path, name, date, comments, contactId);
    }

    public static AbstractEntity createContact(ResultSet resultSet) throws DaoException {
        AbstractEntity contact;
        try {
            long id = resultSet.getLong("id_contact");
            String name = resultSet.getString("contact_name");
            String surname = resultSet.getString("surname");
            String patronymic = resultSet.getString("patronymic");
            LocalDate birthday = DateConverter.convertToLocalDate(resultSet.getDate("birthday"));
            Gender gender = Gender.getGender(resultSet.getString("gender"));
            String citizenship = resultSet.getString("citizenship");
            String familyStatus = resultSet.getString("family_status");
            String website = resultSet.getString("website");
            String email = resultSet.getString("email");
            String work = resultSet.getString("work_place");
            String country = resultSet.getString("country");
            String city = resultSet.getString("city");
            String location = resultSet.getString("address");
            String zipcode = resultSet.getString("zipcode");
            long photoId = resultSet.getLong("id_photo");
            contact = new Contact(id, name, surname, patronymic, birthday, gender,
                    citizenship, familyStatus, website, email, work,
                    country, city, location, zipcode, photoId, null);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Cannot build contact. Error has occurred. ", e);
            throw new DaoException(e);
        }
        return contact;
    }

    public static AbstractEntity createContact(HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String patronymic = request.getParameter("patronymic");
        LocalDate birthday = LocalDate.parse(request.getParameter("birthday"));
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
        long photoId = Long.parseLong(request.getParameter("photoId"));
        return new Contact(0L, name, surname, patronymic, birthday, gender,
                citizenship, familyStatus, website, email, work,
                country, city, location, zipcode, photoId, null);
    }

    public static AbstractEntity createPhone(ResultSet resultSet) throws DaoException {
        AbstractEntity phone;
        try {
            long id = resultSet.getLong("id_phone");
            String countryCode = resultSet.getString("country_code");
            String operatorCode = resultSet.getString("operator_code");
            String number = resultSet.getString("phone_number");
            String comments = resultSet.getString("comments");
            PhoneType type = PhoneType.getPhoneType(resultSet.getString("phone_type"));
            long contactId = resultSet.getLong("id_contact");
            phone = new Phone(id, countryCode, operatorCode, number, type, comments, contactId);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Cannot build attachment. Error has occurred. ", e);
            throw new DaoException(e);
        }
        return phone;
    }

    public static AbstractEntity createPhone(HttpServletRequest request) {
        String countryCode = request.getParameter("p_code");
        String operatorCode = request.getParameter("p_operator");
        String number = request.getParameter("number");
        String comments = request.getParameter("p_comments");
        PhoneType type = PhoneType.getPhoneType(request.getParameter("p_type"));
        long contactId = Long.parseLong(request.getParameter("contactId")); //сначала создать контакт, потом получить его айди, потом передать в телефон
        return new Phone(0L, countryCode, operatorCode, number, type, comments, contactId);
    }

    public static AbstractEntity createPhoto(ResultSet resultSet) throws DaoException {
        AbstractEntity photo;
        try {
            long id = resultSet.getLong("id_photo");
            String path = PathParser.parse(resultSet.getString("photo_path"));
            String name = PathParser.parse(resultSet.getString("photo_name"));
            photo = new Photo(id, path, name);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Cannot build attachment. Error has occurred. ", e);
            throw new DaoException(e);
        }
        return photo;
    }

    public static AbstractEntity createPhoto(HttpServletRequest request) {
        String path = request.getParameter("photo_path");
        String name = request.getParameter("photo_name");
        return new Photo(0L, path, name);
    }

}

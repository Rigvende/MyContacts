package com.itechart.contacts.domain.entity;

import com.itechart.contacts.domain.entity.impl.*;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.util.DateConverter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    //column names:
    private final static String ID_CONTACT = "id_contact";
    private final static String CONTACT_NAME = "contact_name";
    private final static String SURNAME = "surname";
    private final static String PATRONYMIC = "patronymic";
    private final static String BIRTHDAY = "birthday";
    private final static String GENDER = "gender";
    private final static String CITIZENSHIP = "citizenship";
    private final static String STATUS = "family_status";
    private final static String WEBSITE = "website";
    private final static String EMAIL = "email";
    private final static String WORK = "work_place";
    private final static String ID_PHOTO = "id_photo";
    private final static String COUNTRY = "country";
    private final static String CITY = "city";
    private final static String LOCATION = "address";
    private final static String ZIPCODE = "zipcode";
    private final static String DELETED = "deleted";
    private final static String ID_ATTACHMENT = "id_attachment";
    private final static String ATTACHMENT = "attachment_path";
    private final static String NAME = "attachment_name";
    private final static String DATE = "load_date";
    private final static String COMMENTS = "comments";
    private final static String CODE = "country_code";
    private final static String OPERATOR = "operator_code";
    private final static String NUMBER = "phone_number";
    private final static String TYPE = "phone_type";
    private final static String ID_PHONE = "id_phone";
    private final static String PHOTO = "photo_path";

    public static AbstractEntity createAttachment(ResultSet resultSet) throws DaoException {
        AbstractEntity attachment;
        try {
            long id = resultSet.getLong(ID_ATTACHMENT);
            String path = resultSet.getString(ATTACHMENT);
            String name = resultSet.getString(NAME);
            LocalDate date = DateConverter.convertToLocalDate(resultSet.getDate(DATE));
            String comments = resultSet.getString(COMMENTS);
            long contactId = resultSet.getLong(ID_CONTACT);
            attachment = new Attachment(id, path, name, date, comments, contactId);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Cannot build attachment. Error has occurred. ", e);
            throw new DaoException(e);
        }
        return attachment;
    }

    public static AbstractEntity createContact(ResultSet resultSet) throws DaoException {
        AbstractEntity contact;
        try {
            long id = resultSet.getLong(ID_CONTACT);
            String name = resultSet.getString(CONTACT_NAME);
            String surname = resultSet.getString(SURNAME);
            String patronymic = resultSet.getString(PATRONYMIC);
            LocalDate birthday = DateConverter.convertToLocalDate(resultSet.getDate(BIRTHDAY));
            Gender gender = Gender.getGender(resultSet.getString(GENDER));
            String citizenship = resultSet.getString(CITIZENSHIP);
            String familyStatus = resultSet.getString(STATUS);
            String website = resultSet.getString(WEBSITE);
            String email = resultSet.getString(EMAIL);
            String work = resultSet.getString(WORK);
            String country = resultSet.getString(COUNTRY);
            String city = resultSet.getString(CITY);
            String location = resultSet.getString(LOCATION);
            String zipcode = resultSet.getString(ZIPCODE);
            long photoId = resultSet.getLong(ID_PHOTO);
            Timestamp deleted = resultSet.getTimestamp(DELETED);
            contact = new Contact(id, name, surname, patronymic, birthday, gender,
                    citizenship, familyStatus, website, email, work,
                    country, city, location, zipcode, photoId, deleted);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Cannot build contact. Error has occurred. ", e);
            throw new DaoException(e);
        }
        return contact;
    }

    public static AbstractEntity createPhone(ResultSet resultSet) throws DaoException {
        AbstractEntity phone;
        try {
            long id = resultSet.getLong(ID_PHONE);
            String countryCode = resultSet.getString(CODE);
            String operatorCode = resultSet.getString(OPERATOR);
            String number = resultSet.getString(NUMBER);
            String comments = resultSet.getString(COMMENTS);
            PhoneType type = PhoneType.getPhoneType(resultSet.getString(TYPE));
            long contactId = resultSet.getLong(ID_CONTACT);
            phone = new Phone(id, countryCode, operatorCode, number, type, comments, contactId);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Cannot build attachment. Error has occurred. ", e);
            throw new DaoException(e);
        }
        return phone;
    }

    public static AbstractEntity createPhoto(ResultSet resultSet) throws DaoException {
        AbstractEntity photo;
        try {
            long id = resultSet.getLong(ID_PHOTO);
            String path = resultSet.getString(PHOTO);
            photo = new Photo(id, path);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Cannot build attachment. Error has occurred. ", e);
            throw new DaoException(e);
        }
        return photo;
    }

}


package com.itechart.contacts.domain.dao.impl;

import com.itechart.contacts.domain.dao.AbstractDao;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityBuilder;
import com.itechart.contacts.domain.entity.impl.Contact;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.util.DateConverter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for actions mostly with {@link Contact} using connections, statements and queries
 * according with DAO and database data
 * @author Marianna Patrusova
 * @version 1.0
 */
public class ContactDao extends AbstractDao<AbstractEntity> {

    private final static Logger LOGGER = LogManager.getLogger();
    private final static String SQL_ADD_CONTACT =
            "INSERT INTO contacts (contact_name, surname, patronymic, " +
            "birthday, gender, citizenship, family_status, website, email, work_place, " +
            "country, city, address, zipcode, id_photo) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final static String SQL_UPDATE_DELETED =
            "UPDATE contacts SET deleted = ? WHERE id_contact = ?;";
    private final static String SQL_UPDATE_CONTACT =
            "UPDATE contacts " +
            "SET contact_name = ?, surname = ?, patronymic = ?, birthday = ?, " +
            "gender = ?, citizenship = ?, family_status = ?, website = ?, email = ?, work_place = ? " +
            "country = ?, city = ?, address = ?, zipcode = ? " +
            "WHERE id_contact = ?;";
    private static final String SQL_FIND_ALL_CONTACTS =
            "SELECT id_contact, contact_name, surname, patronymic, " +
            "birthday, gender, citizenship, family_status, website, email, work_place, " +
            "country, city, address, zipcode, id_photo " +
            "FROM contacts WHERE deleted IS NULL ORDER BY surname;";
    private final static String SQL_FIND_CONTACT_BY_ID =
            "SELECT id_contact, contact_name, surname, patronymic, " +
            "birthday, gender, citizenship, family_status, website, email, work_place, " +
            "country, city, address, zipcode, id_photo, deleted " +
            "FROM contacts WHERE id_contact = ? AND deleted IS NULL;";
    private static final String SQL_FIND_ALL_BY_BIRTHDAY =
            "SELECT contact_name, surname, patronymic " +
            "FROM contacts WHERE deleted IS NULL AND birthday LIKE ?;";
    private static final String SQL_FIND_EMAIL_BY_ID =
            "SELECT email FROM contacts WHERE deleted IS NULL AND id_contact = ?;";

    public ContactDao() throws DaoException, ClassNotFoundException {
        super();
    }

    @Override
    public AbstractEntity create(AbstractEntity entity) throws DaoException {
        AutoCommitDisable();
        Contact contact = (Contact) entity;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_CONTACT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getSurname());
            preparedStatement.setString(3, contact.getPatronymic());
            preparedStatement.setDate(4, DateConverter.convertToSqlDate(contact.getBirthday()));
            preparedStatement.setString(5, contact.getGender().getValue());
            preparedStatement.setString(6, contact.getCitizenship());
            preparedStatement.setString(7, contact.getFamilyStatus());
            preparedStatement.setString(8, contact.getWebsite());
            preparedStatement.setString(9, contact.getEmail());
            preparedStatement.setString(10, contact.getWork());
            preparedStatement.setString(11, contact.getCountry());
            preparedStatement.setString(12, contact.getCity());
            preparedStatement.setString(13, contact.getAddress());
            preparedStatement.setString(14, contact.getZipcode());
            preparedStatement.setLong(15, contact.getPhotoId());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    contact.setContactId(resultSet.getLong(1));  //get generated key for the new contact
                } else {
                    contact = null;
                }
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();                      //rollback whole transaction if smth goes wrong
            } catch (SQLException ex) {
                LOGGER.log(Level.ERROR,
                        "Cannot do rollback in ContactDao create method. ", e);
                throw new DaoException(e);
            }
            LOGGER.log(Level.ERROR,
                    "Cannot add contact. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return contact;
    }

    @Override
    public boolean delete(AbstractEntity entity) {
        return false;
    }

    @Override
    public boolean update(AbstractEntity entity) throws DaoException {
        AutoCommitDisable();
        Contact contact = (Contact) entity;
        boolean isUpdated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_CONTACT)) {
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getSurname());
            preparedStatement.setString(3, contact.getPatronymic());
            preparedStatement.setDate(4, DateConverter.convertToSqlDate(contact.getBirthday()));
            preparedStatement.setString(5, contact.getGender().getValue());
            preparedStatement.setString(6, contact.getCitizenship());
            preparedStatement.setString(7, contact.getFamilyStatus());
            preparedStatement.setString(8, contact.getWebsite());
            preparedStatement.setString(9, contact.getEmail());
            preparedStatement.setString(10, contact.getWork());
            preparedStatement.setString(11, contact.getCountry());
            preparedStatement.setString(12, contact.getCity());
            preparedStatement.setString(13, contact.getAddress());
            preparedStatement.setString(14, contact.getZipcode());
            preparedStatement.setLong(15, contact.getContactId());
            int update = preparedStatement.executeUpdate();
            if (update == 1) {                              //check if row is updated (0 - false, 1 - true)
                isUpdated = true;
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();                  //rollback whole transaction if smth goes wrong
                } catch (SQLException ex) {
                    LOGGER.log(Level.ERROR,
                            "Cannot do rollback in ContactDao update method. ", e);
                    throw new DaoException(e);
                }
            }
            LOGGER.log(Level.ERROR,
                    "Cannot update contact. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return isUpdated;
    }

    @Override
    public List<AbstractEntity> findAll() throws DaoException {
        AutoCommitDisable();
        List<AbstractEntity> contacts = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_CONTACTS)) {
                while (resultSet.next()) {
                    AbstractEntity contact = EntityBuilder.createContact(resultSet);
                    contacts.add(contact);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();                  //rollback whole transaction if smth goes wrong
                } catch (SQLException ex) {
                    LOGGER.log(Level.ERROR,
                            "Cannot do rollback in ContactDao findAll method. ", e);
                    throw new DaoException(e);
                }
            }
            LOGGER.log(Level.ERROR,
                    "Cannot find contact list. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return contacts;
    }

    @Override
    public AbstractEntity findEntityById(long id) throws DaoException {
        AutoCommitDisable();
        AbstractEntity contact = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CONTACT_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    contact = EntityBuilder.createContact(resultSet);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();                  //rollback whole transaction if smth goes wrong
                } catch (SQLException ex) {
                    LOGGER.log(Level.ERROR,
                            "Cannot do rollback in ContactDao findEntityById method. ", e);
                    throw new DaoException(e);
                }
            }
            LOGGER.log(Level.ERROR,
                    "Cannot find contact by ID. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return contact;
    }

    //special method for soft deleting (fill field "deleted" in db)
    public boolean softDelete(long id) throws DaoException {
        AutoCommitDisable();
        boolean isDeleted = false;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_DELETED)) {
            preparedStatement.setTimestamp(1, timestamp);
            preparedStatement.setLong(2, id);
            int update = preparedStatement.executeUpdate();
            if (update == 1) {                              //check if row is updated (0 - false, 1 - true)
                isDeleted = true;
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();                  //rollback whole transaction if smth goes wrong
                } catch (SQLException ex) {
                    LOGGER.log(Level.ERROR,
                            "Cannot do rollback in ContactDao softDelete method. ", e);
                    throw new DaoException(e);
                }
            }
            LOGGER.log(Level.ERROR,
                    "Cannot soft delete contact. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return isDeleted;
    }

    //special method for finding contacts by birthday for email sending
    public List<String> findAllByBirthday() throws DaoException {
        AutoCommitDisable();
        List<String> contacts = new ArrayList<>();
        LocalDate today = LocalDate.now();
        String date = "%" + today.getMonthValue() + "-" + today.getDayOfMonth();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_BY_BIRTHDAY)) {
            preparedStatement.setString(1, date);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String contact = resultSet.getString("surname") + " " +
                                     resultSet.getString("contact_name") + " " +
                                     resultSet.getString("patronymic");
                    contacts.add(contact);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();                  //rollback whole transaction if smth goes wrong
                } catch (SQLException ex) {
                    LOGGER.log(Level.ERROR,
                            "Cannot do rollback in ContactDao findAllByBirthday method. ", e);
                    throw new DaoException(e);
                }
            }
            LOGGER.log(Level.ERROR,
                    "Cannot find birthday list. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return contacts;
    }

    //special method for finding contacts using filter
    public List<AbstractEntity> findAllByFilter(String query) throws DaoException {
        AutoCommitDisable();
        List<AbstractEntity> contacts = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    AbstractEntity contact = EntityBuilder.createContact(resultSet);
                    contacts.add(contact);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();                  //rollback whole transaction if smth goes wrong
                } catch (SQLException ex) {
                    LOGGER.log(Level.ERROR,
                            "Cannot do rollback in ContactDao findAllByFilter method. ", e);
                    throw new DaoException(e);
                }
            }
            LOGGER.log(Level.ERROR,
                    "Cannot find filtered list. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return contacts;
    }

    public String findEmailById(long id) throws DaoException {
        AutoCommitDisable();
        String email = "";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_EMAIL_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    email = resultSet.getString(1);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();                  //rollback whole transaction if smth goes wrong
                } catch (SQLException ex) {
                    LOGGER.log(Level.ERROR,
                            "Cannot do rollback in ContactDao findEmailById method. ", e);
                    throw new DaoException(e);
                }
            }
            LOGGER.log(Level.ERROR,
                    "Cannot find email by ID. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return email;
    }

}

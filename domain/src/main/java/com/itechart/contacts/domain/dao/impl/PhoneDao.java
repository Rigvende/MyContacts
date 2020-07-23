package com.itechart.contacts.domain.dao.impl;

import com.itechart.contacts.domain.dao.AbstractDao;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityBuilder;
import com.itechart.contacts.domain.entity.impl.Phone;
import com.itechart.contacts.domain.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for actions mostly with {@link Phone} using connections, statements and queries
 * according with DAO and database data
 * @author Marianna Patrusova
 * @version 1.0
 */
public class PhoneDao extends AbstractDao<AbstractEntity> {

    private final static Logger LOGGER = LogManager.getLogger();
    private final static String SQL_ADD_PHONE =
            "INSERT INTO phones (id_contact, country_code, operator_code, phone_number, phone_type, comments) " +
            "VALUES (?, ?, ?, ?, ?, ?);";
    private final static String SQL_DELETE_PHONE =
            "DELETE FROM phones WHERE id_phone = ?;";
    private final static String SQL_UPDATE_PHONE =
            "UPDATE phones " +
            "SET country_code = ?, operator_code = ?, phone_number = ?, phone_type = ?, comments = ? " +
            "WHERE id_phone = ?;";
    private static final String SQL_FIND_ALL_PHONES =
            "SELECT id_phone, id_contact, country_code, operator_code, phone_number, phone_type, comments " +
                    "FROM phones;";
    private final static String SQL_FIND_PHONE_BY_ID =
            "SELECT id_phone, id_contact, country_code, operator_code, phone_number, phone_type, comments " +
                    "FROM phones WHERE id_phone = ?;";
    private final static String SQL_FIND_CONTACT_PHONES =
            "SELECT id_phone, id_contact, country_code, operator_code, phone_number, phone_type, comments " +
                    "FROM phones WHERE id_contact = ?;";

    public PhoneDao() throws DaoException, ClassNotFoundException {
        super();
    }

    @Override
    public AbstractEntity create(AbstractEntity entity) throws DaoException {
        AutoCommitDisable();
        Phone phone = (Phone) entity;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL_ADD_PHONE, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, phone.getContactId());
            preparedStatement.setString(2, phone.getCountryCode());
            preparedStatement.setString(3, phone.getOperatorCode());
            preparedStatement.setString(4, phone.getNumber());
            preparedStatement.setString(5, phone.getType().getValue());
            preparedStatement.setString(6, phone.getComments());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    phone.setPhoneId(resultSet.getLong(1));  //get generated key for the new phone
                } else {
                    phone = null;
                }
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();                      //rollback whole transaction if smth goes wrong
            } catch (SQLException ex) {
                LOGGER.log(Level.ERROR,
                        "Cannot do rollback in PhoneDao create method. ", e);
                throw new DaoException(e);
            }
            LOGGER.log(Level.ERROR,
                    "Cannot add phone. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return phone;
    }

    @Override
    public boolean delete(AbstractEntity entity) throws DaoException {
        AutoCommitDisable();
        boolean isDeleted = false;
        Phone phone = (Phone) entity;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_PHONE)) {
            long id = phone.getPhoneId();
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            if (findEntityById(id) == null) {
                isDeleted = true;                           //check if phone is really deleted in db
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();                  //rollback whole transaction if smth goes wrong
                } catch (SQLException ex) {
                    LOGGER.log(Level.ERROR,
                            "Cannot do rollback in PhoneDao delete method. ", e);
                    throw new DaoException(e);
                }
            }
            LOGGER.log(Level.ERROR,
                    "Cannot delete phone. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return isDeleted;
    }

    @Override
    public boolean update(AbstractEntity entity) throws DaoException {
        AutoCommitDisable();
        Phone phone = (Phone) entity;
        boolean isUpdated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PHONE)) {
            preparedStatement.setString(1, phone.getCountryCode());
            preparedStatement.setString(2, phone.getOperatorCode());
            preparedStatement.setString(3, phone.getNumber());
            preparedStatement.setString(4, phone.getType().getValue());
            preparedStatement.setString(5, phone.getComments());
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
                            "Cannot do rollback in PhoneDao update method. ", e);
                    throw new DaoException(e);
                }
            }
            LOGGER.log(Level.ERROR,
                    "Cannot update phone. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return isUpdated;
    }

    @Override
    public List<AbstractEntity> findAll() throws DaoException {
        AutoCommitDisable();
        List<AbstractEntity> phones = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_PHONES)) {
                while (resultSet.next()) {
                    AbstractEntity phone = EntityBuilder.createPhone(resultSet);
                    phones.add(phone);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();                  //rollback whole transaction if smth goes wrong
                } catch (SQLException ex) {
                    LOGGER.log(Level.ERROR,
                            "Cannot do rollback in PhoneDao findAll method. ", e);
                    throw new DaoException(e);
                }
            }
            LOGGER.log(Level.ERROR,
                    "Cannot find phone list. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return phones;
    }

    @Override
    public AbstractEntity findEntityById(long id) throws DaoException {
        AutoCommitDisable();
        AbstractEntity phone = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PHONE_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    phone = EntityBuilder.createPhone(resultSet);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();                  //rollback whole transaction if smth goes wrong
                } catch (SQLException ex) {
                    LOGGER.log(Level.ERROR,
                            "Cannot do rollback in PhoneDao findEntityById method. ", e);
                    throw new DaoException(e);
                }
            }
            LOGGER.log(Level.ERROR,
                    "Cannot find phone by ID. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return phone;
    }

    public List<AbstractEntity> findByContactId(long id) throws DaoException {
        AutoCommitDisable();
        List<AbstractEntity> phones = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CONTACT_PHONES)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    AbstractEntity phone = EntityBuilder.createPhone(resultSet);
                    phones.add(phone);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();                  //rollback whole transaction if smth goes wrong
                } catch (SQLException ex) {
                    LOGGER.log(Level.ERROR,
                            "Cannot do rollback in PhonesDao findByContactId method. ", e);
                    throw new DaoException(e);
                }
            }
            LOGGER.log(Level.ERROR,
                    "Cannot find phones by contact's ID. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return phones;
    }

}

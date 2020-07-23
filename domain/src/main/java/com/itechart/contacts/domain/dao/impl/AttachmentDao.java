package com.itechart.contacts.domain.dao.impl;

import com.itechart.contacts.domain.dao.AbstractDao;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityBuilder;
import com.itechart.contacts.domain.entity.impl.Attachment;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.util.DateConverter;
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
 * Class for actions mostly with {@link Attachment} using connections, statements and queries
 * according with DAO and database data
 * @author Marianna Patrusova
 * @version 1.0
 */
public class AttachmentDao extends AbstractDao<AbstractEntity> {

    private final static Logger LOGGER = LogManager.getLogger();
    private final static String SQL_ADD_ATTACHMENT =
            "INSERT INTO attachments (attachment_path, attachment_name, load_date, comments, id_contact) " +
            "VALUES (?, ?, ?, ?, ?);";
    private final static String SQL_DELETE_ATTACHMENT =
            "DELETE FROM attachments WHERE id_attachment = ?;";
    private final static String SQL_UPDATE_ATTACHMENT =
            "UPDATE attachments " +
            "SET attachment_name = ?, comments = ? " +
            "WHERE id_attachment = ?;";
    private static final String SQL_FIND_ALL_ATTACHMENTS =
            "SELECT id_attachment, attachment_path, attachment_name, load_date, comments, id_contact " +
            "FROM attachments;";
    private final static String SQL_FIND_ATTACHMENT_BY_ID =
            "SELECT id_attachment, attachment_path, attachment_name, load_date, comments, id_contact " +
            "FROM attachments WHERE id_attachment = ?;";
    private final static String SQL_FIND_CONTACT_ATTACHMENTS =
            "SELECT id_attachment, attachment_path, attachment_name, load_date, comments, id_contact " +
            "FROM attachments WHERE id_contact = ?;";

    public AttachmentDao() throws DaoException, ClassNotFoundException {
        super();
    }

    @Override
    public AbstractEntity create(AbstractEntity entity) throws DaoException {
        AutoCommitDisable();
        Attachment attachment = (Attachment) entity;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL_ADD_ATTACHMENT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, attachment.getPath());
            preparedStatement.setString(2, attachment.getName());
            preparedStatement.setDate(3, DateConverter.convertToSqlDate(attachment.getLoadDate()));
            preparedStatement.setString(4, attachment.getComments());
            preparedStatement.setLong(5, attachment.getContactId());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {                     //get generated key for the new attachment
                    attachment.setAttachmentId(resultSet.getLong(1));
                } else {
                    attachment = null;
                }
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();                      //rollback whole transaction if smth goes wrong
            } catch (SQLException ex) {
                LOGGER.log(Level.ERROR,
                        "Cannot do rollback in AttachmentDao create method. ", e);
                throw new DaoException(e);
            }
            LOGGER.log(Level.ERROR,
                    "Cannot add attachment. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return attachment;
    }

    @Override
    public boolean delete(AbstractEntity entity) throws DaoException {
        AutoCommitDisable();
        boolean isDeleted = false;
        Attachment attachment = (Attachment) entity;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ATTACHMENT)) {
            long id = attachment.getAttachmentId();
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            if (findEntityById(id) == null) {
                isDeleted = true;                           //check if attachment is really deleted in db
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();                  //rollback whole transaction if smth goes wrong
                } catch (SQLException ex) {
                    LOGGER.log(Level.ERROR,
                            "Cannot do rollback in AttachmentDao delete method. ", e);
                    throw new DaoException(e);
                }
            }
            LOGGER.log(Level.ERROR,
                    "Cannot delete attachment. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return isDeleted;
    }

    @Override
    public boolean update(AbstractEntity entity) throws DaoException {
        AutoCommitDisable();
        Attachment attachment = (Attachment) entity;
        boolean isUpdated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ATTACHMENT)) {
            preparedStatement.setString(1, attachment.getName());
            preparedStatement.setString(2, attachment.getComments());
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
                            "Cannot do rollback in AttachmentDao update method. ", e);
                    throw new DaoException(e);
                }
            }
            LOGGER.log(Level.ERROR,
                    "Cannot update attachment. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return isUpdated;
    }

    @Override
    public List<AbstractEntity> findAll() throws DaoException {
        AutoCommitDisable();
        List<AbstractEntity> attachments = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_ATTACHMENTS)) {
                while (resultSet.next()) {
                    AbstractEntity attachment = EntityBuilder.createAttachment(resultSet);
                    attachments.add(attachment);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();                  //rollback whole transaction if smth goes wrong
                } catch (SQLException ex) {
                    LOGGER.log(Level.ERROR,
                            "Cannot do rollback in AttachmentDao findAll method. ", e);
                    throw new DaoException(e);
                }
            }
            LOGGER.log(Level.ERROR,
                    "Cannot find attachment list. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return attachments;
    }

    @Override
    public AbstractEntity findEntityById(long id) throws DaoException {
        AutoCommitDisable();
        AbstractEntity attachment = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ATTACHMENT_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    attachment = EntityBuilder.createAttachment(resultSet);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();                  //rollback whole transaction if smth goes wrong
                } catch (SQLException ex) {
                    LOGGER.log(Level.ERROR,
                            "Cannot do rollback in AttachmentDao findEntityById method. ", e);
                    throw new DaoException(e);
                }
            }
            LOGGER.log(Level.ERROR,
                    "Cannot find attachment by ID. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return attachment;
    }

    public List<AbstractEntity> findByContactId(long id) throws DaoException {
        AutoCommitDisable();
        List<AbstractEntity> attachments = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CONTACT_ATTACHMENTS)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    AbstractEntity attachment = EntityBuilder.createAttachment(resultSet);
                    attachments.add(attachment);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();                  //rollback whole transaction if smth goes wrong
                } catch (SQLException ex) {
                    LOGGER.log(Level.ERROR,
                            "Cannot do rollback in AttachmentDao findByContactId method. ", e);
                    throw new DaoException(e);
                }
            }
            LOGGER.log(Level.ERROR,
                    "Cannot find attachments by contact's ID. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return attachments;
    }

}

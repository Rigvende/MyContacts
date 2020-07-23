package com.itechart.contacts.domain.dao;

import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.util.DbcpManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Abstract class as parent for all DAO classes
 * @author Marianna Patrusova
 * @version 1.0
 */
public abstract class AbstractDao<T extends AbstractEntity> {

    private final static Logger LOGGER = LogManager.getLogger();
    protected Connection connection;

    public AbstractDao() throws DaoException, ClassNotFoundException {
        connection = DbcpManager.getConnection();
    }

    /**
     * Method: create database table's row depending on AbstractEntity instance.
     * @param entity is type of {@link AbstractEntity}
     * @return long (generated id of new db row)
     */
    public abstract AbstractEntity create(T entity) throws DaoException;

    /**
     * Method: delete database table's row depending on AbstractEntity instance.
     * @throws DaoException object
     * @param entity is type of {@link AbstractEntity}
     * @return boolean
     */
    public abstract boolean delete(T entity) throws DaoException;

    /**
     * Method: update database table's row depending on AbstractEntity instance.
     * @throws DaoException object
     * @param entity is type of {@link AbstractEntity}
     * @return boolean
     */
    public abstract boolean update(T entity) throws DaoException;

    /**
     * Method: find all rows in database table depending on method's realization
     * and create suitable AbstractEntity object.
     * @throws DaoException object
     * @return List of found T type {@link AbstractEntity} objects
     */
    public abstract List<T> findAll() throws DaoException;

    /**
     * Method: find suitable row in database table and create AbstractEntity instance
     * @throws DaoException object
     * @param id is table's id of object
     * @return type T of {@link AbstractEntity}
     */
    public abstract T findEntityById(long id) throws DaoException;

    //after processing all concrete dao's operations at service layer - don't forget to close connection
    public void exit() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARN,"Connection closing is failed", e);
            }
        }
    }

    //disable auto-commit in DAO methods for rollback opportunity
    protected void AutoCommitDisable() throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"Cannot set autocommit false in DAO layer. ", e);
            throw new DaoException(e);
        }
    }

}

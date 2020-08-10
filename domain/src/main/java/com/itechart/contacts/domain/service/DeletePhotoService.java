package com.itechart.contacts.domain.service;

import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.dao.impl.PhotoDao;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;

/**
 * Class-service for deleting photos
 * @author Marianna Patrusova
 * @version 1.0
 */
public class DeletePhotoService {

    private final static Logger LOGGER = LogManager.getLogger();

    public boolean service (AbstractEntity entity, Connection connection) throws ServiceException {
        boolean isDeleted;
        PhotoDao dao = null;
        try {
            dao = (PhotoDao) DaoFactory.createDao(EntityType.PHOTO, connection);
            isDeleted = dao.delete(entity);
        } catch (ClassNotFoundException | DaoException e) {
            LOGGER.log(Level.ERROR, "Cannot delete photo. Error has occurred. ", e);
            throw new ServiceException(e);
        }
        return isDeleted;
    }

}

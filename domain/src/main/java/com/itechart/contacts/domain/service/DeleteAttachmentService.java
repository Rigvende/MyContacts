package com.itechart.contacts.domain.service;

import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.dao.impl.AttachmentDao;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;

/**
 * Class-service for deleting attachments
 * @author Marianna Patrusova
 * @version 1.0
 */
public class DeleteAttachmentService {

    private final static Logger LOGGER = LogManager.getLogger();

    public boolean service (AbstractEntity entity, Connection connection) throws ServiceException {
        boolean isDeleted;
        AttachmentDao dao;
        try {
            dao = (AttachmentDao) DaoFactory.createDao(EntityType.ATTACHMENT, connection);
            isDeleted = dao.delete(entity);
        } catch (ClassNotFoundException | DaoException e) {
            LOGGER.log(Level.ERROR, "Cannot delete attachment. Error has occurred. ", e);
            throw new ServiceException(e);
        }
        return isDeleted;
    }

}

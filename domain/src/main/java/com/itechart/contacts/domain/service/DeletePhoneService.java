package com.itechart.contacts.domain.service;

import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.dao.impl.PhoneDao;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class-service for deleting phones
 * @author Marianna Patrusova
 * @version 1.0
 */
public class DeletePhoneService {

    private final static Logger LOGGER = LogManager.getLogger();

    public boolean service (AbstractEntity entity) throws ServiceException {
        boolean isDeleted;
        PhoneDao dao = null;
        try {
            dao = (PhoneDao) DaoFactory.createDao(EntityType.PHONE);
            isDeleted = dao.delete(entity);
        } catch (ClassNotFoundException | DaoException e) {
            LOGGER.log(Level.ERROR, "Cannot delete phone. Error has occurred. ", e);
            throw new ServiceException(e);
        } finally {
            if (dao != null) {
                dao.exit();
            }
        }
        return isDeleted;
    }

}

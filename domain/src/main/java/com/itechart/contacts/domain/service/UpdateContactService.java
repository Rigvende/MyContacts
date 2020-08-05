package com.itechart.contacts.domain.service;

import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.dao.impl.ContactDao;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.entity.impl.Contact;
import com.itechart.contacts.domain.entity.impl.Gender;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

/**
 * Class-service for creating / updating contacts
 *
 * @author Marianna Patrusova
 * @version 1.0
 */
public class UpdateContactService {

    private final static Logger LOGGER = LogManager.getLogger();

    //update
    public boolean service(long id, Contact contact) throws ServiceException {
        ContactDao dao = null;
        try {
            dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT);
            if (dao.findEntityById(id) != null) {
                return dao.update(contact);
            } else {
                return false;
            }
        } catch (ClassNotFoundException | DaoException e) {
            LOGGER.log(Level.ERROR, "Cannot update contact. Error has occurred. ", e);
            throw new ServiceException(e);
        } finally {
            if (dao != null) {
                dao.exit();
            }
        }
    }

    //create
    public AbstractEntity service(Contact contact) throws ServiceException {
        ContactDao dao = null;
        try {
            dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT);
            return dao.create(contact);
        } catch (ClassNotFoundException | DaoException e) {
            LOGGER.log(Level.ERROR, "Cannot create contact. Error has occurred. ", e);
            throw new ServiceException(e);
        } finally {
            if (dao != null) {
                dao.exit();
            }
        }
    }

}

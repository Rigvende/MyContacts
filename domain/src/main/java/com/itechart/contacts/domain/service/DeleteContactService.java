package com.itechart.contacts.domain.service;

import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.dao.impl.ContactDao;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;

/**
 * Class-service for deleting contacts
 * @author Marianna Patrusova
 * @version 1.0
 */
public class DeleteContactService {

    private final static Logger LOGGER = LogManager.getLogger();

    //update = soft delete (set timestamp value in field deleted)
    public boolean service(Connection connection, long... ids) throws ServiceException {
        if (ids != null) {
            ContactDao dao;
            try {
                dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT, connection);
                boolean flag = false;
                for (long id : ids) {
                    flag = dao.softDelete(id);
                }
                return flag;
            } catch (ClassNotFoundException | DaoException e) {
                LOGGER.log(Level.ERROR, "Cannot update contact. Error has occurred. ", e);
                throw new ServiceException(e);
            }
        } else {
            return false;
        }
    }

}

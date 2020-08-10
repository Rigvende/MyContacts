package com.itechart.contacts.domain.service;

import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.dao.impl.PhoneDao;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.entity.impl.Phone;
import com.itechart.contacts.domain.entity.impl.PhoneType;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

/**
 * Class-service for creating / updating phones
 * @author Marianna Patrusova
 * @version 1.0
 */
public class UpdatePhoneService {

    private final static Logger LOGGER = LogManager.getLogger();

    //update
    public boolean service(long id, String country, String operator, String number, String type,
                           String comments, Connection connection) throws ServiceException {
        Phone phone;
        PhoneDao dao;
        try {
            dao = (PhoneDao) DaoFactory.createDao(EntityType.PHONE, connection);
            phone = (Phone) dao.findEntityById(id);
            phone.setCountryCode(country);
            phone.setOperatorCode(operator);
            phone.setNumber(number);
            PhoneType phoneType = PhoneType.getPhoneType(type);
            phone.setType(phoneType);
            phone.setComments(comments);
            return dao.update(phone);
        } catch (ClassNotFoundException | DaoException e) {
            LOGGER.log(Level.ERROR, "Cannot update phone. Error has occurred. ", e);
            throw new ServiceException(e);
        }
    }

    //create
    public AbstractEntity service(Phone phone, Connection connection) throws ServiceException {
        PhoneDao dao;
        try {
            dao = (PhoneDao) DaoFactory.createDao(EntityType.PHONE, connection);
            return dao.create(phone);
        } catch (ClassNotFoundException | DaoException e) {
            LOGGER.log(Level.ERROR, "Cannot create phone. Error has occurred. ", e);
            throw new ServiceException(e);
        }
    }

}

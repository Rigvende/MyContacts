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
 * @author Marianna Patrusova
 * @version 1.0
 */
public class UpdateContactService {

    private final static Logger LOGGER = LogManager.getLogger();

    //update
    public boolean service(long id, String... data) throws ServiceException {
        Contact contact;
        ContactDao dao = null;
        try {
            dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT);
            contact = (Contact) dao.findEntityById(id);
            contact.setName(data[0]);
            contact.setSurname(data[1]);
            contact.setPatronymic(data[2]);
            contact.setBirthday(LocalDate.parse(data[3]));
            Gender gender = Gender.getGender(data[4]);
            contact.setGender(gender);
            contact.setCitizenship(data[5]);
            contact.setFamilyStatus(data[6]);
            contact.setWebsite(data[7]);
            contact.setEmail(data[8]);
            contact.setWork(data[9]);
            contact.setCountry(data[10]);
            contact.setCity(data[11]);
            contact.setAddress(data[12]);
            contact.setZipcode(data[13]);
            return dao.update(contact);
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

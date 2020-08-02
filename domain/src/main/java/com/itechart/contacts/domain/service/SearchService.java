package com.itechart.contacts.domain.service;

import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.dao.impl.ContactDao;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.entity.impl.Contact;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import java.util.List;

/**
 * Class-service for searching contacts
 * @author Marianna Patrusova
 * @version 1.0
 */
public class SearchService {

    private final static Logger LOGGER = LogManager.getLogger();

    public String service(String query) throws ServiceException {
        List<AbstractEntity> filterResult;
        ContactDao dao = null;
        try {
            dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT);
            filterResult = dao.findAllByFilter(query);
            StringBuilder json = new StringBuilder("[\n");
            for (AbstractEntity entity: filterResult) {
                Contact contact = (Contact) entity;
                json.append("{\n");
                createCoreJson(contact, json);
            }
            return json.toString().substring(0, json.length() - 2) + "\n]";
        } catch (DaoException | ClassNotFoundException e) {
            LOGGER.log(Level.ERROR, "Error while filter searching has occurred. ", e);
            throw new ServiceException(e);
        } finally {
            if (dao != null) {
                dao.exit();
            }
        }
    }

    private void createCoreJson(Contact contact, StringBuilder json) {
        json.append("\"id\": ").append(JSONObject.numberToString(contact.getContactId())).append(",\n");
        json.append("\"name\": ").append(JSONObject.quote(contact.getName())).append(",\n");
        json.append("\"surname\": ").append(JSONObject.quote(contact.getSurname())).append(",\n");
        json.append("\"patronymic\": ").append(JSONObject.quote(contact.getPatronymic())).append(",\n");
        json.append("\"country\": ").append(JSONObject.quote(contact.getCountry())).append(",\n");
        json.append("\"city\": ").append(JSONObject.quote(contact.getCity())).append(",\n");
        json.append("\"address\": ").append(JSONObject.quote(contact.getAddress())).append(",\n");
        json.append("\"birthday\": ").append(JSONObject.valueToString(contact.getBirthday())).append(",\n");
        json.append("\"work\": ").append(JSONObject.quote(contact.getWork())).append("\n");
        json.append("},\n");
    }

}

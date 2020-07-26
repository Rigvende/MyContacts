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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class-service for searching contacts
 * @author Marianna Patrusova
 * @version 1.0
 */
public class SearchService {

    private final static Logger LOGGER = LogManager.getLogger();

    public List<AbstractEntity> service(Contact filter, String addCondition) throws ServiceException {
        int counter = 0;
        String query = "SELECT id_contact, contact_name, surname, patronymic, birthday, gender, citizenship, " +
                "family_status, website, email, work_place, country, city, address, zipcode, id_photo " +
                "FROM contacts WHERE ";
        if (filter.getName() != null && !filter.getName().isEmpty()) {
            query += "contact_name = ? AND ";
            counter++;
        }
        if (filter.getSurname() != null && !filter.getSurname().isEmpty()) {
            query += "surname = ? AND ";
            counter++;
        }
        if (filter.getPatronymic() != null && !filter.getPatronymic().isEmpty()) {
            query += "patronymic = ? AND ";
            counter++;
        }
        if (filter.getFamilyStatus() != null && !filter.getFamilyStatus().isEmpty()) {
            query += "family_status = ? AND ";
            counter++;
        }
        if (filter.getCitizenship() != null && !filter.getCitizenship().isEmpty()) {
            query += "citizenship = ? AND ";
            counter++;
        }
        if (filter.getCountry() != null && !filter.getCountry().isEmpty()) {
            query += "country = ? AND ";
            counter++;
        }
        if (filter.getCity() != null && !filter.getCity().isEmpty()) {
            query += "city = ? AND ";
            counter++;
        }
        if (filter.getAddress() != null && !filter.getAddress().isEmpty()) {
            query += "address = ? AND ";
            counter++;
        }
        if (filter.getGender() != null) {
            query += "gender = ? AND ";
            counter++;
        }
        if (filter.getBirthday() != null) {
            switch (addCondition) {
                case "strict":
                    query += "birthday = ? AND ";
                    counter++;
                    break;
                case "lesser":
                    query += "birthday < ? AND ";
                    counter++;
                    break;
                case "greater":
                    query += "birthday > ? AND ";
                    counter++;
                    break;
            }
        }
        List<AbstractEntity> filterResult;
        ContactDao dao = null;
        try {
            dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT);
            filterResult = dao.findAllByFilter(query, counter);
        } catch (DaoException | ClassNotFoundException e) {
            LOGGER.log(Level.ERROR, "Error while filter searching has occurred. ", e);
            throw new ServiceException(e);
        } finally {
            if (dao != null) {
                dao.exit();
            }
        }
        return filterResult;
    }

//    public List<Contact> service(Contact filterParameters) throws ServiceException {
//        List<Contact> filterResult = new ArrayList<>();
//        ContactDao dao = null;
//        try {
//            dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT);
//            List<AbstractEntity> all = dao.findAll();
//            for (AbstractEntity each : all) {
//                Contact contact = (Contact) each;
//                filterResult.add(contact);
//            }
//            List<Function<Contact, String>> comparingFields = Arrays.asList(Contact::getName, //fixme
//                    Contact::getSurname, Contact::getPatronymic, Contact::getFamilyStatus,
//                    Contact::getStringBirthday, Contact::getStringGender, Contact::getCitizenship,
//                    Contact::getCountry, Contact::getCity, Contact::getAddress);
//            filterResult = filter(filterResult, filterParameters, comparingFields);
//        } catch (DaoException | ClassNotFoundException e) {
//            LOGGER.log(Level.ERROR, "Error while filter searching has occurred. ", e);
//            throw new ServiceException(e);
//        } finally {
//            if (dao != null) {
//                dao.exit();
//            }
//        }
//        return filterResult;
//    }
//
//    private List<Contact> filter(List<Contact> contacts, Contact filter,
//                                 List<Function<Contact, String>> comparingFields) {
//        return contacts.stream()
//                .filter(contact -> compareFields(contact, filter, comparingFields))
//                .collect(Collectors.toList());
//    }
//
//    private boolean compareFields(Contact contact, Contact filter,
//                                  List<Function<Contact, String>> comparingFields) {
//        return comparingFields.stream()
//                .allMatch(func -> func.apply(contact)
//                        .contains(func.apply(filter)));
//    }

}

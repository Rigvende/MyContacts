package com.itechart.contacts.domain.dao.impl;

import com.itechart.contacts.domain.ConnectionTestInstance;
import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.entity.impl.Contact;
import com.itechart.contacts.domain.entity.impl.Gender;
import com.itechart.contacts.domain.exception.DaoException;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ContactDaoTest {

    private Connection connection;

    @Test
    void createDelete() throws ClassNotFoundException, DaoException, SQLException {
        connection = ConnectionTestInstance.createConnection();
        ContactDao dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT, connection);
        Contact contact = new Contact();
        contact.setPhotoId(1);
        contact.setName("aaa");
        contact.setSurname("bbb");
        contact.setPatronymic("");
        contact.setBirthday(LocalDate.of(2000, 1, 1));
        contact.setGender(Gender.FEMALE);
        contact.setCitizenship("");
        contact.setFamilyStatus("");
        contact.setWebsite("");
        contact.setEmail("");
        contact.setWork("");
        contact.setCountry("");
        contact.setCity("");
        contact.setAddress("");
        contact.setZipcode("");
        contact = (Contact) dao.create(contact);
        assertTrue(contact.getContactId() != 0);
        assertTrue(dao.softDelete(contact.getContactId()));
        connection.close();
    }

    @Test
    void update() throws ClassNotFoundException, DaoException, SQLException {
        connection = ConnectionTestInstance.createConnection();
        ContactDao dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT, connection);
        Contact contact = new Contact();
        contact.setContactId(118);
        contact.setName("Лидия");
        contact.setSurname("Стыкова");
        contact.setPatronymic("");
        contact.setBirthday(LocalDate.of(2000, 1, 1));
        contact.setGender(Gender.FEMALE);
        contact.setCitizenship("");
        contact.setFamilyStatus("");
        contact.setWebsite("");
        contact.setEmail("");
        contact.setWork("");
        contact.setCountry("");
        contact.setCity("");
        contact.setAddress("");
        contact.setZipcode("");
        assertTrue(dao.update(contact));
        connection.close();
    }

    @Test
    void findAll() throws ClassNotFoundException, DaoException, SQLException {
        connection = ConnectionTestInstance.createConnection();
        ContactDao dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT, connection);
        List<AbstractEntity> list = dao.findAll();
        boolean flag = false;
        for (AbstractEntity entity: list) {
            Contact contact = (Contact) entity;
            if (contact.getContactId() == 1) {
                flag = true;
                break;
            }
        }
        assertTrue(flag);
        connection.close();
    }

    @Test
    void findEntityById() throws ClassNotFoundException, DaoException, SQLException {
        connection = ConnectionTestInstance.createConnection();
        ContactDao dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT, connection);
        Contact contact  = (Contact) dao.findEntityById(1);
        assertNotNull(contact);
        connection.close();
    }

    @Test
    void findAllByBirthday() throws ClassNotFoundException, DaoException, SQLException {
        connection = ConnectionTestInstance.createConnection();
        ContactDao dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT, connection);
        List<String> list = dao.findAllByBirthday();
        assertNotNull(list);
        connection.close();
    }

    @Test
    void findAllByFilter() throws SQLException, ClassNotFoundException, DaoException {
        connection = ConnectionTestInstance.createConnection();
        ContactDao dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT, connection);
        String query = "SELECT id_contact, contact_name, surname, patronymic, birthday, gender, citizenship, " +
                "family_status, website, email, work_place, country, city, address, zipcode, id_photo " +
                "FROM contacts WHERE patronymic = 'Иванович';";
        List<AbstractEntity> list = dao.findAllByFilter(query);
        boolean flag = false;
        for (AbstractEntity entity: list) {
            Contact contact = (Contact) entity;
            if (contact.getName().equals("Иван")) {
                flag = true;
                break;
            }
        }
        assertTrue(flag);
        connection.close();
    }

}

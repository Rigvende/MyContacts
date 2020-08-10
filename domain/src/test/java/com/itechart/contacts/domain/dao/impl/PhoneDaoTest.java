package com.itechart.contacts.domain.dao.impl;

import com.itechart.contacts.domain.ConnectionTestInstance;
import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.entity.impl.Phone;
import com.itechart.contacts.domain.entity.impl.PhoneType;
import com.itechart.contacts.domain.exception.DaoException;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PhoneDaoTest {

    private Connection connection;

    @Test
    void createDelete() throws ClassNotFoundException, DaoException, SQLException {
        connection = ConnectionTestInstance.createConnection();
        PhoneDao dao = (PhoneDao) DaoFactory.createDao(EntityType.PHONE, connection);
        Phone phone = new Phone();
        phone.setContactId(1);
        phone.setCountryCode("");
        phone.setOperatorCode("");
        phone.setNumber("2555055");
        phone.setType(PhoneType.HOME);
        phone.setComments("");
        phone = (Phone) dao.create(phone);
        assertTrue(phone.getPhoneId() != 0);
        assertTrue(dao.delete(phone));
        connection.close();
    }

    @Test
    void update() throws ClassNotFoundException, DaoException, SQLException {
        connection = ConnectionTestInstance.createConnection();
        PhoneDao dao = (PhoneDao) DaoFactory.createDao(EntityType.PHONE, connection);
        Phone phone = new Phone();
        phone.setPhoneId(1);
        phone.setCountryCode("+375");
        phone.setOperatorCode("44");
        phone.setNumber("2555055");
        phone.setType(PhoneType.MOBILE);
        phone.setComments("");
        assertTrue(dao.update(phone));
        connection.close();
    }

    @Test
    void findAll() throws ClassNotFoundException, DaoException, SQLException {
        connection = ConnectionTestInstance.createConnection();
        PhoneDao dao = (PhoneDao) DaoFactory.createDao(EntityType.PHONE, connection);
        List<AbstractEntity> list = dao.findAll();
        boolean flag = false;
        for (AbstractEntity entity: list) {
            Phone phone = (Phone) entity;
            if (phone.getContactId() == 1) {
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
        PhoneDao dao = (PhoneDao) DaoFactory.createDao(EntityType.PHONE, connection);
        Phone phone = (Phone)dao.findEntityById(1);
        assertNotNull(phone);
        connection.close();
    }

    @Test
    void findByContactId() throws ClassNotFoundException, DaoException, SQLException {
        connection = ConnectionTestInstance.createConnection();
        PhoneDao dao = (PhoneDao) DaoFactory.createDao(EntityType.PHONE, connection);
        List<AbstractEntity> list = dao.findByContactId(1);
        assertFalse(list.isEmpty());
        connection.close();
    }
}
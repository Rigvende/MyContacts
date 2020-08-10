package com.itechart.contacts.domain.dao;

import com.itechart.contacts.domain.ConnectionTestInstance;
import com.itechart.contacts.domain.dao.impl.ContactDao;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.exception.DaoException;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import static org.junit.jupiter.api.Assertions.*;

class DaoFactoryTest {

    @Test
    void createDao() throws ClassNotFoundException, DaoException, SQLException {
        Connection connection = ConnectionTestInstance.createConnection();
        assertTrue(DaoFactory.createDao(EntityType.CONTACT, connection) instanceof ContactDao);
        connection.close();
    }
}
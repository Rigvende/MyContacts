package com.itechart.contacts.web.util;

import com.itechart.contacts.domain.exception.DaoException;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DbcpManagerTest {

    @Test
    void createConnection() throws ClassNotFoundException, DaoException {
        Connection connection = DbcpManager.createConnection();
        assertNotNull(connection);
    }
}
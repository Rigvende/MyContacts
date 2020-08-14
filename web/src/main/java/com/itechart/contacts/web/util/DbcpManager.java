package com.itechart.contacts.web.util;

import com.itechart.contacts.domain.exception.DaoException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Class for connection manipulations
 * @author Marianna Patrusova
 * @version 1.0
 */
public class DbcpManager {

    private final static Logger LOGGER = LogManager.getLogger();
    private final static String USER = "user";
    private final static String PASS = "pass";
    private final static String URL = "url";
    private final static String MIN_IDLE = "min.idle";
    private final static String MAX_IDLE = "max.idle";
    private final static String MAX_PS = "max.open.prepared.statements";
    private final static String BUNDLE = "connectionDB";
    private final static String MYSQL_DRIVER = "com.mysql.cj.jdbc.Driver";

    //create connection using data source pool
    public static Connection createConnection() throws DaoException, ClassNotFoundException {
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE);
        String user = bundle.getString(USER);
        String pass = bundle.getString(PASS);
        String url = bundle.getString(URL);
        int minIdle = Integer.parseInt(bundle.getString(MIN_IDLE));
        int maxIdle = Integer.parseInt(bundle.getString(MAX_IDLE));
        int maxPs = Integer.parseInt(bundle.getString(MAX_PS));
        Class.forName(MYSQL_DRIVER);
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(pass);
        ds.setMinIdle(minIdle);
        ds.setMaxIdle(maxIdle);
        ds.setMaxOpenPreparedStatements(maxPs);
        Connection connection;
        try {
            connection = ds.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Connection's creation failed. ", e);
            throw new DaoException(e);
        }
        return connection;
    }

    //get connection from pool
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DbcpManager.createConnection();
            AutoCommitDisable(connection);
        } catch (DaoException | ClassNotFoundException e) {
            LOGGER.log(Level.ERROR,"Cannot take connection from pool", e);
        }
        return connection;
    }

    //return connection to pool
    public static void exit(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARN,"Connection closing is failed", e);
            }
        }
    }

    //rollback connection
    public static void rollBack(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                LOGGER.log(Level.WARN,"Connection rollback is failed", e);
            }
        }
    }

    //disable auto-commit for rollback opportunity
    public static void AutoCommitDisable(Connection connection) throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"Cannot set autocommit false", e);
            throw new DaoException(e);
        }
    }

}

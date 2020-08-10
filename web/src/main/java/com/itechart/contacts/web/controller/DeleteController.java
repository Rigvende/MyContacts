package com.itechart.contacts.web.controller;

import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import com.itechart.contacts.domain.service.DeleteContactService;
import com.itechart.contacts.domain.util.DbcpManager;
import com.itechart.contacts.web.validator.StringValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class-controller for delete contacts operations.
 * @author Marianna Patrusova
 * @version 1.0
 */
@WebServlet(urlPatterns = "/delete")
public class DeleteController extends HttpServlet {

    private static final long serialVersionUID = 938035871806818474L;
    private final static Logger LOGGER = LogManager.getLogger();
    private final static String SPACE = " ";
    private final DeleteContactService service = new DeleteContactService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection connection = take();
        String ids = request.getParameter("ids");
        long[] deleteIds = parseId(ids);
        try {
            service.service(connection, deleteIds);
            connection.commit();
            LOGGER.log(Level.INFO, "User deletes contacts: " + ids);
        } catch (ServiceException | SQLException e) {
            rollBack(connection);
            LOGGER.log(Level.ERROR, "Request process of deleting contacts failed.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Что-то пошло не так...");
        } finally {
            exit(connection);
        }
    }

    //метод возвращает массив айди для удаления
    private long[] parseId(String data) {
        String[] ids = data.split(SPACE);
        int size = ids.length;
        long[] parsed = new long[size];
        for (int i = 0; i < parsed.length; i++) {
            if (StringValidator.isValidId(ids[i])) {
                parsed[i] = Long.parseLong(ids[i]);
            }
        }
        return parsed;
    }

    //get connection from pool
    private Connection take() {
        Connection connection = null;
        try {
            connection = DbcpManager.getConnection();
            AutoCommitDisable(connection);
        } catch (DaoException | ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.log(Level.ERROR,"Cannot take connection from pool", e);
        }
        return connection;
    }

    //return connection to pool
    private void exit(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                LOGGER.log(Level.WARN,"Connection closing is failed", e);
            }
        }
    }

    //rollback connection
    private void rollBack(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
                LOGGER.log(Level.WARN,"Connection rollback is failed", e);
            }
        }
    }

    //disable auto-commit for rollback opportunity
    private void AutoCommitDisable(Connection connection) throws DaoException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"Cannot set autocommit false", e);
            throw new DaoException(e);
        }
    }

}

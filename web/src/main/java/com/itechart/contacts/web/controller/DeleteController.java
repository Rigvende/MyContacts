package com.itechart.contacts.web.controller;

import com.itechart.contacts.domain.exception.ServiceException;
import com.itechart.contacts.domain.service.DeleteContactService;
import com.itechart.contacts.web.util.DbcpManager;
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
        Connection connection = DbcpManager.getConnection();
        String ids = request.getParameter("ids");
        long[] deleteIds = parseId(ids);
        try {
            service.service(connection, deleteIds);
            connection.commit();
            LOGGER.log(Level.INFO, "User deletes contacts: " + ids);
        } catch (ServiceException | SQLException e) {
            DbcpManager.rollBack(connection);
            LOGGER.log(Level.ERROR, "Request process of deleting contacts failed.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Что-то пошло не так...");
        } finally {
            DbcpManager.exit(connection);
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

}

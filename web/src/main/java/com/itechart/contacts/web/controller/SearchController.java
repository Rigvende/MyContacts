package com.itechart.contacts.web.controller;

import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import com.itechart.contacts.domain.service.SearchService;
import com.itechart.contacts.domain.util.DbcpManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class for searching operations controller.
 * @author Marianna Patrusova
 * @version 1.0
 */
@WebServlet(urlPatterns = "/search")
public class SearchController extends HttpServlet {

    private static final long serialVersionUID = 7649477230906914706L;
    private final static Logger LOGGER = LogManager.getLogger();
    private final static String UTF_8 = "UTF-8";
    private final static String TYPE = "text/html; charset=UTF-8";
    private final SearchService searchService = new SearchService();

    //выполняем поиск контактов по полученным данным
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection connection = take();
        response.setCharacterEncoding(UTF_8);
        response.setContentType(TYPE);
        try (PrintWriter out = response.getWriter()) {
            String query = buildQuery(request).toString();
            String json = searchService.service(query, connection);
            out.println(json);
            connection.commit();
            LOGGER.log(Level.INFO, "User uses filter for searching contacts");
        } catch (ServiceException | SQLException e) {
            LOGGER.log(Level.ERROR, "Request process of sending mail failed.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Что-то пошло не так...");
        } finally {
            exit(connection);
        }
    }

    //формирование запроса к бд на основании данных фильтра
    private StringBuilder buildQuery(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder("SELECT id_contact, contact_name, surname, patronymic, ");
        builder.append("birthday, gender, citizenship, family_status, website, email, work_place, ")
                .append("country, city, address, zipcode, id_photo FROM contacts WHERE ");
        String name = request.getParameter("contactName");
        if (name != null && !name.isEmpty()) {
            builder.append("contact_name = '").append(name).append("' AND ");
        }
        String surname = request.getParameter("surname");
        if (surname != null && !surname.isEmpty()) {
            builder.append("surname = '").append(surname).append("' AND ");
        }
        String patronymic = request.getParameter("patronymic");
        if (patronymic != null && !patronymic.isEmpty()) {
            builder.append("patronymic = '").append(patronymic).append("' AND ");
        }
        String birthday = request.getParameter("birthday");
        if (birthday != null && !birthday.isEmpty()) {
            String condition = request.getParameter("condition"); //after, before, strict
            if (condition != null && !condition.isEmpty()) {
                switch (condition) {
                    case "strict":
                        builder.append("birthday = '").append(birthday).append("' AND ");
                        break;
                    case "before":
                        builder.append("birthday < '").append(birthday).append("' AND ");
                        break;
                    case "after":
                        builder.append("birthday > '").append(birthday).append("' AND ");
                        break;
                }
            }
        }
        String gender = request.getParameter("gender");
        if (gender != null && !gender.isEmpty()) {
            builder.append("gender = '").append(gender).append("' AND ");
        }
        String citizenship = request.getParameter("citizenship");
        if (citizenship != null && !citizenship.isEmpty()) {
            builder.append("citizenship = '").append(citizenship).append("' AND ");
        }
        String country = request.getParameter("country");
        if (country != null && !country.isEmpty()) {
            builder.append("country = '").append(country).append("' AND ");
        }
        String city = request.getParameter("city");
        if (city != null && !city.isEmpty()) {
            builder.append("city = '").append(city).append("' AND ");
        }
        String address = request.getParameter("street");
        if (address != null && !address.isEmpty()) {
            builder.append("address LIKE '%").append(address).append("%' AND ");
        }
        builder.append("deleted IS NULL;");
        return builder;
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

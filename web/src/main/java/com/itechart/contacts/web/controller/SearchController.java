package com.itechart.contacts.web.controller;

import com.itechart.contacts.domain.exception.ServiceException;
import com.itechart.contacts.domain.service.SearchService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class for mail operations controller.
 * @author Marianna Patrusova
 * @version 1.0
 */
@WebServlet(urlPatterns = "/search")
public class SearchController extends HttpServlet {

    private static final long serialVersionUID = 7649477230906914706L;
    private final static Logger LOGGER = LogManager.getLogger();
    private final SearchService searchService = new SearchService();

    //выполняем поиск контактов по полученным данным
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String query = buildQuery(request).toString();
            String json = searchService.service(query);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println(json);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Request process of sending mail failed.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Что-то пошло не так...");
        }
    }

    //формирование запроса к бд на основании данных фильтра
    private StringBuilder buildQuery(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder("SELECT id_contact, contact_name, surname, patronymic, ");
        builder.append("birthday, gender, citizenship, family_status, website, email, work_place, ")
                .append("country, city, address, zipcode, id_photo  FROM contacts WHERE ");
        String name = request.getParameter("name");
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
        String address = request.getParameter("address");
        if (address != null && !address.isEmpty()) {
            builder.append("address = '").append(address).append("' AND ");
        }
        builder.append("deleted IS NULL;");
        return builder;
    }

}

package com.itechart.contacts.web.controller;

import com.itechart.contacts.domain.entity.impl.Contact;
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

/**
 * Class for mail operations controller.
 * @author Marianna Patrusova
 * @version 1.0
 */
@WebServlet(urlPatterns = "/search/")
public class SearchController extends HttpServlet {

    private static final long serialVersionUID = 7649477230906914706L;
    private final static Logger LOGGER = LogManager.getLogger();
    private final SearchService searchService = new SearchService();

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Contact contact = new Contact();//fixme
        String name = request.getParameter("name");
        //
        try {
            searchService.service(contact);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Request process of sending mail failed.");
            response.sendError(500);
        }
    }

}

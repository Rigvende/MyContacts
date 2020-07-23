package com.itechart.contacts.web.controller;

import com.itechart.contacts.domain.exception.ServiceException;
import com.itechart.contacts.domain.service.GetContactsService;
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
 * Class for contacts operations controller.
 * @author Marianna Patrusova
 * @version 1.0
 */
@WebServlet(urlPatterns = "/contacts/*")
public class ContactsController extends HttpServlet {

    private static final long serialVersionUID = 8362021663142387750L;
    private final static Logger LOGGER = LogManager.getLogger();
    private final static String CONTEXT = "/view_war/contacts/"; //artifact war, context /view_war
    private final GetContactsService getContactsService = new GetContactsService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestUrl = request.getRequestURI();
        String parameter = requestUrl.substring(CONTEXT.length());
        try {
            String json = getContactsService.service(parameter);
            response.setCharacterEncoding("UTF-8");  //response in ISO-8859-1
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println(json);
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "Requested url not found");
            response.sendError(404);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Request process of finding contacts failed.");
            response.sendError(500);
        }
    }

}

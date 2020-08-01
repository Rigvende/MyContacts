package com.itechart.contacts.web.controller;

import com.itechart.contacts.domain.exception.ServiceException;
import com.itechart.contacts.domain.service.MailService;
import com.itechart.contacts.web.validator.StringValidator;
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
@WebServlet(urlPatterns = "/mail/*")
public class MailController extends HttpServlet {

    private static final long serialVersionUID = -4687623009995660337L;
    private final static Logger LOGGER = LogManager.getLogger();
    private final static String CONTEXT = "/view_war/mail/"; //artifact war, context /view_war
    private final static String MESSAGE_FAIL = "Что-то пошло не так...";
    private final MailService mailService = new MailService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestUrl = request.getRequestURI();
        String id = requestUrl.substring(CONTEXT.length()); //get contact id from url if exists
        try {
            if (!id.isEmpty() && StringValidator.isValidId(id)) {
                long contactId = Long.parseLong(id);
                String json = mailService.service(contactId);
                System.out.println(json);
                response.setCharacterEncoding("UTF-8");  //response in ISO-8859-1, very bad for db data
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println(json);
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Request process of getting mail info failed.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, MESSAGE_FAIL);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String to = request.getParameter("to");
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");
        try {
            if (StringValidator.isValidEmail(to) &&
                    StringValidator.isValidHeader(subject) &&
                    StringValidator.isValidMessage(body)) {
                mailService.service(to, subject, body);
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Request process of sending mail failed.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, MESSAGE_FAIL);
        }
    }

}

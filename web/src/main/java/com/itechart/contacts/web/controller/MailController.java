package com.itechart.contacts.web.controller;

import com.itechart.contacts.domain.exception.ServiceException;
import com.itechart.contacts.domain.service.MailService;
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
 *
 * @author Marianna Patrusova
 * @version 1.0
 */
@WebServlet(urlPatterns = "/mail/*")
public class MailController extends HttpServlet {

    private static final long serialVersionUID = -4687623009995660337L;
    private final static Logger LOGGER = LogManager.getLogger();
    private final static String CONTEXT = "/view_war/mail/"; //artifact war, context /view_war fixme
    private final MailService mailService = new MailService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestUrl = request.getRequestURI();
        String id = requestUrl.substring(CONTEXT.length()); //get contact id from url if exists
        try {
            if (!id.isEmpty()) {
                long contactId = Long.parseLong(id);
                String json = mailService.service(contactId);
                response.setCharacterEncoding("UTF-8");  //response in ISO-8859-1, very bad for db data
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println(json);
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Request process of getting mail info failed.");
            response.sendError(500);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String sender = request.getParameter("to");
        String subject = request.getParameter("subject");
        String message = request.getParameter("body");
        try {
            mailService.service(sender, subject, message);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Request process of sending mail failed.");
            response.sendError(500);
        }
    }

}

package com.itechart.contacts.web.controller;

import com.itechart.contacts.domain.entity.impl.Contact;
import com.itechart.contacts.domain.exception.ServiceException;
import com.itechart.contacts.domain.service.MailService;
import com.itechart.contacts.web.util.DbcpManager;
import com.itechart.contacts.web.validator.StringValidator;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
    private final static String UTF_8 = "UTF-8";
    private final static String TEMPLATES = "templates";
    private final static String MAIL = "mail.ftl";
    private final static String TYPE = "text/html; charset=UTF-8";
    private final static String CONTACT = "contact";
    private final static String TO = "to";
    private final static String SUBJECT = "subject";
    private final static String BODY = "body";
    private final MailService mailService = new MailService();

    //получаем данные по id из чекбокса
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection connection = DbcpManager.getConnection();
        String requestUrl = request.getRequestURI();
        String id = requestUrl.substring(CONTEXT.length()); //get contact id from url if exists
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);
        cfg.setDefaultEncoding(UTF_8);
        cfg.setServletContextForTemplateLoading(getServletContext(), TEMPLATES);
        response.setCharacterEncoding(UTF_8);
        response.setContentType(TYPE);
        try (Writer out = response.getWriter()){
            if (!id.isEmpty() && StringValidator.isValidId(id)) {
                long contactId = Long.parseLong(id);
                Contact contact = mailService.service(contactId, connection);
                Map<String, Object> root = new HashMap<>();
                root.put(CONTACT, contact);
                Template temp = cfg.getTemplate(MAIL);
                temp.process(root, out);
                connection.commit();
            }
        } catch (TemplateException | ServiceException | SQLException e) {
            DbcpManager.rollBack(connection);
            LOGGER.log(Level.ERROR, "Request process of getting mail info failed.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, MESSAGE_FAIL);
        } finally {
            DbcpManager.exit(connection);
        }
    }

    //отправляем почту
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(UTF_8);
        String to = request.getParameter(TO);
        String subject = request.getParameter(SUBJECT);
        String body = request.getParameter(BODY);
        try {
            if (StringValidator.isValidEmail(to) &&
                    StringValidator.isValidTextLength(subject) &&
                    StringValidator.isValidMessage(body)) {
                mailService.service(to, subject, body);
                LOGGER.log(Level.INFO, "User sends email");
            } else {
                response.sendError(500, MESSAGE_FAIL);
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Request process of sending mail failed.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, MESSAGE_FAIL);
        }
    }

}

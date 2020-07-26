package com.itechart.contacts.domain.service;

import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.dao.impl.ContactDao;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.entity.impl.Contact;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Class-service logic for sending mails
 * @author Marianna Patrusova
 * @version 1.0
 */
public class MailService {

    private final static Logger LOGGER = LogManager.getLogger();
    private static final String PATH_CONFIG = "mail.properties";
    private static final String USER = "mail.user";
    private static final String PASSWORD = "mail.password";

    public boolean service(String receiver, String header, String letter) throws ServiceException {
        Properties properties = loadProperties();
        final String senderMailbox = properties.getProperty(USER);
        final String senderPassword = properties.getProperty(PASSWORD);
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderMailbox, senderPassword);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderMailbox));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(header);
            message.setText(letter);
            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.log(Level.ERROR, "Error while sending e-mail has occurred. ", e);
            throw new ServiceException(e);
        }
        return true;
    }

    private Properties loadProperties(){
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(MailService.PATH_CONFIG)) {
            assert inputStream != null;
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "Error while reading properties for e-mail has occurred. ", e);
            throw new RuntimeException(e);
        }
        return properties;
    }

    //get data from db for e-mailing confirmation
    public String service(long id) throws ServiceException {
        String json;
        ContactDao dao = null;
        try {
            dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT);
            Contact contact = (Contact) dao.findEntityById(id);
            json = createMailJson(contact);
        } catch (DaoException | ClassNotFoundException e) {
            LOGGER.log(Level.ERROR, "Error while sending work confirmation by e-mail has occurred. ", e);
            throw new ServiceException(e);
        } finally {
            if (dao != null) {
                dao.exit();
            }
        }
        return json;
    }

    private String createMailJson(Contact contact) {
        StringBuilder json = new StringBuilder("{\n");
        json.append("\"name\": ").append(JSONObject.quote(contact.getName())).append(",\n");
        json.append("\"surname\": ").append(JSONObject.quote(contact.getSurname())).append(",\n");
        json.append("\"email\": ").append(JSONObject.quote(contact.getEmail())).append("\n");
        json.append("},\n");
        return json.toString();
    }

}

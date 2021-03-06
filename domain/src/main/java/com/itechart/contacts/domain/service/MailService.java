package com.itechart.contacts.domain.service;

import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.dao.impl.ContactDao;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.entity.impl.Contact;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
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
        } catch (SendFailedException e) {
            LOGGER.log(Level.ERROR, "Error while sending e-mail has occurred. ", e);
            return false;
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
    public Contact service(long id, Connection connection) throws ServiceException {
        Contact contact ;
        ContactDao dao;
        try {
            dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT, connection);
            contact = (Contact) dao.findEntityById(id);
        } catch (DaoException | ClassNotFoundException e) {
            LOGGER.log(Level.ERROR, "Error while sending work confirmation by e-mail has occurred. ", e);
            throw new ServiceException(e);
        }
        return contact;
    }

}

package com.itechart.contacts.web.scheduler;

import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.dao.impl.ContactDao;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import com.itechart.contacts.domain.service.MailService;
import com.itechart.contacts.domain.util.DbcpManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Class for job declaration (sending birthdays email).
 * @author Marianna Patrusova
 * @version 1.0
 */
public class MailJob implements Job {

    private final static Logger LOGGER = LogManager.getLogger();
    private final static String LETTER = "Сегодня день рождения у ваших контактов: \n";
    private final static String MAIL = "zvezdo4ka13@yandex.by"; //fixme
    private final static String HEADER = "Дни рождения ";

    @Override
    public void execute(JobExecutionContext context) {
        Connection connection = take();
        ContactDao dao;
        StringBuilder message = new StringBuilder(LETTER);
        try {
            dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT, connection);
            List<String> birthdayList = dao.findAllByBirthday();
            for (String person : birthdayList) {
                message.append(person).append("\n");
            }
            MailService service = new MailService();
            String header = HEADER + LocalDate.now() ;
            String body = message.toString();
            service.service(MAIL, header, body);
            connection.commit();
            LOGGER.log(Level.INFO, "Daily birthday list is send");
        } catch (DaoException | ClassNotFoundException | ServiceException | SQLException e) {
            rollBack(connection);
            LOGGER.log(Level.WARN, "Error while sending birthdays by e-mail has occurred. ", e);
        } finally {
            exit(connection);
        }
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

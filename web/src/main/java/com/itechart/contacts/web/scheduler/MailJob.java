package com.itechart.contacts.web.scheduler;

import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.dao.impl.ContactDao;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import com.itechart.contacts.domain.service.MailService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
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
        ContactDao dao = null;
        StringBuilder message = new StringBuilder(LETTER);
        try {
            dao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT);
            List<String> birthdayList = dao.findAllByBirthday();
            for (String person : birthdayList) {
                message.append(person).append("\n");
            }
            MailService service = new MailService();
            String header = HEADER + LocalDate.now() ;
            String body = message.toString();
            service.service(MAIL, header, body);
        } catch (DaoException | ClassNotFoundException | ServiceException e) {
            LOGGER.log(Level.WARN, "Error while sending birthdays by e-mail has occurred. ", e);
        } finally {
            if (dao != null) {
                dao.exit();
            }
        }
    }

}

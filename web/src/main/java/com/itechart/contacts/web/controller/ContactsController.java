package com.itechart.contacts.web.controller;

import com.itechart.contacts.domain.entity.impl.Attachment;
import com.itechart.contacts.domain.entity.impl.Contact;
import com.itechart.contacts.domain.entity.impl.Phone;
import com.itechart.contacts.domain.entity.impl.Photo;
import com.itechart.contacts.domain.exception.ServiceException;
import com.itechart.contacts.domain.service.*;
import com.itechart.contacts.web.scheduler.MailJob;
import com.itechart.contacts.web.util.RequestParser;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Class for contacts operations controller.
 *
 * @author Marianna Patrusova
 * @version 1.0
 */
@WebServlet(urlPatterns = "/contacts/*")
public class ContactsController extends HttpServlet {

    private static final long serialVersionUID = 8362021663142387750L;
    private final static Logger LOGGER = LogManager.getLogger();
    private final static String CONTEXT = "/view_war/contacts/"; //artifact war, context /view_war
    private final GetContactsService getContactsService = new GetContactsService();
    private final UpdateContactService updateContactService = new UpdateContactService();
    private final UpdateAttachmentService updateAttachmentService = new UpdateAttachmentService();
    private final UpdatePhotoService updatePhotoService = new UpdatePhotoService();
    private final UpdatePhoneService updatePhoneService = new UpdatePhoneService();

//    @Override //todo раскомментить
//    public void init() {
//        setScheduler();
//    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestUrl = request.getRequestURI();
        String id = requestUrl.substring(CONTEXT.length()); //get contact id from url if exists
        response.setCharacterEncoding("UTF-8");  //response in ISO-8859-1, very bad for db data
        response.setContentType("text/html; charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String json = getContactsService.service(id);
            out.println(json);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Request process of finding contacts failed.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Что-то пошло не так...");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Contact contact = RequestParser.createContact(request);
        Photo photo = RequestParser.createPhoto(request);
//        List<Phone> phones;
//        List<Attachment> attachments;
        try {
            if (contact.getContactId() != 0L) { //обновить
                updateContactService.service(contact.getContactId(), contact);
                //удалить старое фото, загрузить новое
                updatePhotoService.service(photo.getPhotoId(), photo.getName());
//                updateAttachmentService.service(attachments);
//                updatePhoneService.service(phones);
                LOGGER.log(Level.INFO, "Contact # " + contact.getContactId() + " was updated");
            } else { //создать
                //создать папку, загрузить фото
                photo = (Photo) updatePhotoService.service(photo);
                contact.setPhotoId(photo.getPhotoId());
                Contact d = (Contact) updateContactService.service(contact);
//                updatePhoneService.service(phones);
//                updateAttachmentService.service(attachments);
                LOGGER.log(Level.INFO, "New contact was created");
            }
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Request process of finding contacts failed.");
            response.sendError(500);
        }
    }

    //set background task - daily birthdays checking
    private void setScheduler() {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler;
        try {
            scheduler = sf.getScheduler();
            scheduler.start();
            JobDetail job = newJob(MailJob.class)
                    .withIdentity("job1", "group1")
                    .build();
            CronTrigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(17, 20))
                    .forJob("job1", "group1")
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}

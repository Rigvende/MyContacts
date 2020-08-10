package com.itechart.contacts.web.controller;

import com.itechart.contacts.domain.entity.impl.Contact;
import com.itechart.contacts.domain.entity.impl.Photo;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import com.itechart.contacts.domain.service.*;
import com.itechart.contacts.domain.util.DbcpManager;
import com.itechart.contacts.web.scheduler.MailJob;
import com.itechart.contacts.web.util.RequestParser;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Class for contacts operations controller.
 *
 * @author Marianna Patrusova
 * @version 1.0
 */
@WebServlet(urlPatterns = "/contacts/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class ContactsController extends HttpServlet {

    private static final long serialVersionUID = 8362021663142387750L;
    private final static Logger LOGGER = LogManager.getLogger();
    private final static String CONTEXT = "/view_war/contacts/"; //artifact war, context /view_war
    private final static String UTF_8 = "UTF-8";
    private final static String TYPE = "text/html; charset=UTF-8";
    private final static String JOB = "job1";
    private final static String GROUP = "group1";
    private final static String TRIGGER = "trigger1";
    private final GetContactsService getContactsService = new GetContactsService();
    private final UpdateContactService updateContactService = new UpdateContactService();
    private final UpdatePhotoService updatePhotoService = new UpdatePhotoService();
//    private final UpdateAttachmentService updateAttachmentService = new UpdateAttachmentService();
//    private final UpdatePhoneService updatePhoneService = new UpdatePhoneService();

    @Override
    public void init() {
        setScheduler();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection connection = take();
        String requestUrl = request.getRequestURI();
        String id = requestUrl.substring(CONTEXT.length()); //get contact id from url if exists
        response.setCharacterEncoding(UTF_8);  //response in ISO-8859-1, very bad for db data
        response.setContentType(TYPE);
        try (PrintWriter out = response.getWriter()) {
            String json = getContactsService.service(connection, id);
            out.println(json);
            connection.commit();
        } catch (ServiceException | SQLException e) {
            rollBack(connection);
            e.printStackTrace();
            LOGGER.log(Level.ERROR, "Request process of finding contacts failed.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Что-то пошло не так...");
        } finally {
            exit(connection);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection connection = take();
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            LOGGER.log(Level.ERROR, "Cannot update contact: data is not multipart form");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Что-то пошло не так...");
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 1024);
        factory.setRepository(new File("C:/temp"));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(1024 * 1024 * 5);
        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            Map<String, String> parameters = new HashMap<>();
            int counter = 1;
            FileItem photoItem = null;
            for (FileItem item : fileItems) {
                if (item.isFormField()) {
                    parameters.put(item.getFieldName(), item.getString(UTF_8));
                    System.out.println(item.getFieldName() + "=" + item.getString(UTF_8));
                } else {
                    if (item.getFieldName().equals("picture")) {
                        photoItem = item;
                        parameters.put("photo_name", item.getName());
                        String a = item.getName();
                        System.out.println("photo_name=" + a);
                    } else {
                        parameters.put("file_name" + counter, item.getName());
                        System.out.println("file_name" + counter + item.getName());
                        counter++;
                    }
                }
            }
            System.out.println("file_name1=" + parameters.get("file_name1"));
            Contact contact = RequestParser.createContact(parameters);
            System.out.println(contact);
            Photo photo = RequestParser.createPhoto(parameters);
            System.out.println(photo);
            try {
                if (contact != null && contact.getContactId() != 0L) { //обновить
                    updateContactService.service(contact.getContactId(), contact, connection);
                    if (photo != null && photo.getPhotoId() != 0L) {
                        updatePhotoService.service(photo.getPhotoId(), photo.getName(), connection);
                    }
                    LOGGER.log(Level.INFO, "Contact # " + contact.getContactId() + " was updated");
                } else { //создать
                    photo = (Photo) updatePhotoService.service(photo, connection);
                    System.out.println(photo);
                    if (contact != null) {
                        contact.setPhotoId(photo.getPhotoId());
                    }
                    contact = (Contact) updateContactService.service(contact, connection);
                    System.out.println(contact);
//                    if (photoItem != null) { fixme
//                        writePhoto(photoItem, photo.getPhotoId());
//                    }
                    LOGGER.log(Level.INFO, "New contact was created");
                }
                connection.commit();
            } catch (Exception e) {
                rollBack(connection);
                e.printStackTrace();
                LOGGER.log(Level.ERROR, "Request process of finding contacts failed.");
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Что-то пошло не так...");
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
            LOGGER.log(Level.ERROR, "File is oversized.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Что-то пошло не так...");
        } finally {
            exit(connection);
        }
    }

    //write photo on hard disk
    private void writePhoto(FileItem item, long id) throws Exception {
        String fileName = FilenameUtils.getName(item.getName());
        File file;
        String filePath = "../image/photos/" + id + "/";//fixme
        System.out.println(filePath + fileName);
        file = new File(filePath + fileName);
        item.write(file);
        File server_dir;
        String dirPath = getClass().getResource("/").getPath();
        System.out.println(dirPath);
        server_dir = new File(dirPath + id + "/");  //fixme copy file to server dir
        FileUtils.copyFileToDirectory(file, server_dir); //otherwise we can't see picture after loading
    }
//        request.setCharacterEncoding(UTF_8);
//        Contact contact = RequestParser.createContact(request);
//        Photo photo = RequestParser.createPhoto(request);
////        List<Phone> phones; //fixme
////        List<Attachment> attachments; //todo
//        try {
//            if (contact.getContactId() != 0L) { //обновить
//                updateContactService.service(contact.getContactId(), contact);
//                //удалить старое фото, загрузить новое
//                if (photo.getPhotoId() != 0L) {
//                    updatePhotoService.service(photo.getPhotoId(), photo.getName());
////                updateAttachmentService.service(attachments);
////                updatePhoneService.service(phones);
//                }
//                LOGGER.log(Level.INFO, "Contact # " + contact.getContactId() + " was updated");
//            } else { //создать
//                //создать папку, загрузить фото, получить имя (если фото не пустое), иначе просто сделать запись в бд
//                photo = (Photo) updatePhotoService.service(photo);
//                contact.setPhotoId(photo.getPhotoId());
//                contact = (Contact) updateContactService.service(contact);
////                updatePhoneService.service(phones);
////                updateAttachmentService.service(attachments);
//                LOGGER.log(Level.INFO, "New contact was created");
//            }
//        } catch (ServiceException e) {
//            LOGGER.log(Level.ERROR, "Request process of finding contacts failed.");
//            response.sendError(500);
//        }


    //set background task - daily birthdays checking
    private void setScheduler() {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler;
        try {
            scheduler = sf.getScheduler();
            scheduler.start();
            JobDetail job = newJob(MailJob.class)
                    .withIdentity(JOB, GROUP)
                    .build();
            CronTrigger trigger = newTrigger()
                    .withIdentity(TRIGGER, GROUP)
                    .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(13, 30))
                    .forJob(JOB, GROUP)
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
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

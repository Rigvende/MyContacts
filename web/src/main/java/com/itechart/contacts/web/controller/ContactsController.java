package com.itechart.contacts.web.controller;

import com.itechart.contacts.domain.entity.impl.Contact;
import com.itechart.contacts.domain.entity.impl.Photo;
import com.itechart.contacts.domain.exception.ServiceException;
import com.itechart.contacts.domain.service.*;
import com.itechart.contacts.web.util.DbcpManager;
import com.itechart.contacts.web.scheduler.MailJob;
import com.itechart.contacts.web.util.FileUploader;
import com.itechart.contacts.web.util.RequestParser;
import com.itechart.contacts.web.util.Status;
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
 * Class for contacts operations controller (create, update, show contacts + files uploading).
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
    private String photoPath, filePath, extraPath;
    private final static String PHOTO_INIT_PARAMETER = "photo-upload";  //set according with server machine in web.xml
    private final static String FILE_INIT_PARAMETER = "file-upload";    //set according with server machine in web.xml
    private final static String INIT_PARAMETER_EXT = "file-upload-ext"; //set according with server machine in web.xml
    private final static String CONTEXT = "/view_war/contacts/"; //artifact war, context /view_war
    private final static String UTF_8 = "UTF-8";
    private final static String TYPE = "text/html; charset=UTF-8";
    private final static String JOB = "job1";
    private final static String GROUP = "group1";
    private final static String TRIGGER = "trigger1";
    private final FileUploader uploader = new FileUploader();
    private final GetContactsService getContactsService = new GetContactsService();
    private final UpdateContactService updateContactService = new UpdateContactService();
    private final UpdatePhotoService updatePhotoService = new UpdatePhotoService();
//    private final UpdateAttachmentService updateAttachmentService = new UpdateAttachmentService();
//    private final UpdatePhoneService updatePhoneService = new UpdatePhoneService();

    @Override
    public void init() {
        setScheduler();
        photoPath = getServletContext().getInitParameter(PHOTO_INIT_PARAMETER);
        filePath = getServletContext().getInitParameter(FILE_INIT_PARAMETER);
        extraPath = getServletContext().getInitParameter(INIT_PARAMETER_EXT);
    }

    //show contacts or concrete contact
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection connection = DbcpManager.getConnection();
        String requestUrl = request.getRequestURI();
        String id = requestUrl.substring(CONTEXT.length()); //get contact id from url if exists
        response.setCharacterEncoding(UTF_8);  //response in ISO-8859-1, very bad for db data
        response.setContentType(TYPE);
        try (PrintWriter out = response.getWriter()) {
            String json = getContactsService.service(connection, id);
            out.println(json);
            connection.commit();
        } catch (ServiceException | SQLException e) {
            DbcpManager.rollBack(connection);
            e.printStackTrace();
            LOGGER.log(Level.ERROR, "Request process of finding contacts failed.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Что-то пошло не так...");
        } finally {
            DbcpManager.exit(connection);
        }
    }

    //create or update contacts
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Connection connection = DbcpManager.getConnection();
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            LOGGER.log(Level.ERROR, "Cannot update contact: data is not multipart form");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Что-то пошло не так...");
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 1024);
        factory.setRepository(new File(extraPath));
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
                } else {
                    if (item.getFieldName().equals("picture")) {
                        photoItem = item;
                        parameters.put("photo_name", item.getName());
                    } else {
                        parameters.put("file_name" + counter, item.getName());
                        System.out.println("file_name" + counter + item.getName());
                        counter++;
                    }
                }
            }
            Contact contact = RequestParser.createContact(parameters);
            Photo photo = RequestParser.createPhoto(parameters);
            try {
                if (contact != null && contact.getContactId() != 0L) {
                    //ОБНОВИТЬ
                    updateContactService.service(contact.getContactId(), contact, connection);
                    if (photo != null && photo.getPhotoId() != 0L) {//
                        updatePhotoService.service(photo.getPhotoId(), photo.getName(), connection);
                        if (photoItem != null && !photo.getName().isEmpty()
                                && photo.getStatus().equals(Status.UPDATED.getValue())) {
                            uploader.writePhoto(photoItem, photoPath, photo.getPhotoId());
                        }
                    }
                    LOGGER.log(Level.INFO, "Contact # " + contact.getContactId() + " was updated");
                } else {
                    //СОЗДАТЬ
                    photo = (Photo) updatePhotoService.service(photo, connection);
                    if (contact != null) {
                        contact.setPhotoId(photo.getPhotoId());
                    }
                    updateContactService.service(contact, connection);
                    if (photoItem != null && !photo.getName().isEmpty()) {
                        uploader.writePhoto(photoItem, photoPath, photo.getPhotoId());
                    }
                    LOGGER.log(Level.INFO, "New contact was created");
                }
                connection.commit();
            } catch (Exception e) {
                DbcpManager.rollBack(connection);
                LOGGER.log(Level.ERROR, "Request process of finding contacts failed.");
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Что-то пошло не так...");
            }
        } catch (FileUploadException e) {
            LOGGER.log(Level.ERROR, "File is oversized.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Что-то пошло не так...");
        } finally {
            DbcpManager.exit(connection);
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
                    .withIdentity(JOB, GROUP)
                    .build();
            CronTrigger trigger = newTrigger()
                    .withIdentity(TRIGGER, GROUP)
                    .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(13, 30))
                    .forJob(JOB, GROUP)
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            LOGGER.log(Level.ERROR, "Error in birthday scheduler has occurred");
            e.printStackTrace();
        }
    }

}

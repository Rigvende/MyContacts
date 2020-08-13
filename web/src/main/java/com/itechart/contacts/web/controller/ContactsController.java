package com.itechart.contacts.web.controller;

import com.itechart.contacts.domain.entity.impl.Attachment;
import com.itechart.contacts.domain.entity.impl.Contact;
import com.itechart.contacts.domain.entity.impl.Phone;
import com.itechart.contacts.domain.entity.impl.Photo;
import com.itechart.contacts.domain.exception.ServiceException;
import com.itechart.contacts.domain.service.*;
import com.itechart.contacts.web.util.DbcpManager;
import com.itechart.contacts.web.scheduler.MailJob;
import com.itechart.contacts.web.util.FileUploader;
import com.itechart.contacts.web.util.RequestEntityBuilder;
import com.itechart.contacts.web.util.Status;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import java.util.*;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Class for contacts operations controller (create, update, show contacts + files uploading).
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
    private final DeletePhoneService deletePhoneService = new DeletePhoneService();
    private final DeleteAttachmentService deleteAttachmentService = new DeleteAttachmentService();
    private final UpdateContactService updateContactService = new UpdateContactService();
    private final UpdatePhotoService updatePhotoService = new UpdatePhotoService();
    private final UpdateAttachmentService updateAttachmentService = new UpdateAttachmentService();
    private final UpdatePhoneService updatePhoneService = new UpdatePhoneService();

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
        Connection connection = DbcpManager.getConnection();            //получить коннекшн
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {                                             //проверить, что реквест мультипартовый
            LOGGER.log(Level.ERROR, "Cannot update contact: data is not multipart form");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Что-то пошло не так...");
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();        //обработка файлов
        factory.setSizeThreshold(1024 * 1024);
        factory.setRepository(new File(extraPath));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding(UTF_8);
        upload.setSizeMax(1024 * 1024 * 5);
        try {
            List<FileItem> fileItems = upload.parseRequest(request);    //парсим форму на отдельные инпуты
            Map<String, String> parameters = new HashMap<>();           //мапы для разных данных
            Map<String, String> phoneParameters = new HashMap<>();
            Map<String, String> attachmentParameters = new HashMap<>();
            List<Phone> phones = new ArrayList<>();                     //списки для телефонов и вложений
            List<Attachment> attachments = new ArrayList<>();
            int counter = 1;                    //счетчики для загружаемых файлов
            int paramCounter = counter;
            FileItem photoItem = null;          //отдельно собираем файлы для обработки
            List<FileItem> files = new ArrayList<>();
            for (FileItem item : fileItems) {   //обработка инпутов и распределение данных по мапам
                if (item.isFormField()) {       //обычные поля
                    parameters.put(item.getFieldName(), item.getString(UTF_8));
                    System.out.println(item.getFieldName() + "=" + item.getString(UTF_8));
                    RequestEntityBuilder.fillPhones(phones, item, phoneParameters, parameters.get("idContact"));
                    RequestEntityBuilder.fillAttachments(attachments, item, attachmentParameters,
                            parameters.get("idContact"), parameters.get("file_name" + paramCounter));
                } else {                        //файлы
                    if (item.getFieldName().equals("picture")) {
                        photoItem = item;
                        String fileName = FilenameUtils.getName(item.getName());
                        parameters.put("photo_name", fileName);          //фото
                    } else {
                        files.add(item);
                        String fileName = FilenameUtils.getName(item.getName());
                        parameters.put("file_name" + counter, fileName); //вложение
                        paramCounter = counter;
                        counter++;
                    }
                }
            }
            Contact contact = RequestEntityBuilder.createContact(parameters);
            Photo photo = RequestEntityBuilder.createPhoto(parameters);
            try {
                if (contact != null && contact.getContactId() != 0L) {
                    //ОБНОВИТЬ
                    updateContactService.service(contact.getContactId(), contact, connection);//изменить контакт
                    if (photo != null && photo.getPhotoId() != 0L
                            && photo.getStatus().equals(Status.UPDATED.getValue())) {
                        updatePhotoService.service(photo.getPhotoId(), photo.getName(), connection); //изменить фото
                        if (photoItem != null && !photo.getName().isEmpty()
                                && photo.getStatus().equals(Status.UPDATED.getValue())) {
                            uploader.writePhoto(photoItem, photoPath, photo.getPhotoId()); //перезапись фото
                        }
                    } else if (photo != null && photo.getStatus().equals(Status.DELETED.getValue())) {
                        updatePhotoService.service(photo.getPhotoId(), "", connection); //сброс фото
                    }
                    for (Phone phone : phones) {
                        if (phone != null && phone.getPhoneId() != 0L
                                && phone.getStatus().equals(Status.UPDATED.getValue())) { //изменить телефон
                            updatePhoneService.service(phone.getPhoneId(), phone.getCountryCode(),
                                    phone.getOperatorCode(), phone.getNumber(), phone.getType().getValue(),
                                    phone.getComments(), connection);
                        } else if (phone != null && phone.getPhoneId() != 0L            //удалить телефон
                                && phone.getStatus().equals(Status.DELETED.getValue())) {
                            deletePhoneService.service(phone, connection);
                        } else if (phone != null && phone.getPhoneId() == 0L) {         //создать новый телефон
                            phone.setContactId(contact.getContactId());
                            updatePhoneService.service(phone, connection);
                        }
                    }
                    for (int i = 0; i < attachments.size(); i++) {
                        Attachment attachment = attachments.get(i);
                        if (attachment != null && attachment.getAttachmentId() != 0L) {
                            if (attachment.getStatus().equals(Status.UPDATED.getValue())) { //изменить вложение
                                if (attachment.getPath().isEmpty()) { //если меняется файл
                                    updateAttachmentService.service(attachment.getAttachmentId(),
                                            attachment.getName(), attachment.getComments(), connection);
                                    uploader.deleteFile(filePath, attachment.getAttachmentId());
                                    uploader.writeFile(files.get(i), filePath, attachment.getAttachmentId());
                                } else {                            //если файл не меняется
                                    updateAttachmentService.service(attachment.getAttachmentId(),
                                            attachment.getName(), attachment.getComments(), connection);
                                }
                            }
                            if (attachment.getStatus().equals(Status.DELETED.getValue())) { //удалить вложение
                                deleteAttachmentService.service(attachment, connection);
                                uploader.deleteFile(filePath, attachment.getAttachmentId());
                            }
                        }
                        if (attachment != null && attachment.getAttachmentId() == 0L) { //создать новое вложение
                            attachment.setContactId(contact.getContactId());
                            updateAttachmentService.service(attachment, connection);
                            uploader.writeFile(files.get(i), filePath, attachment.getAttachmentId()); //запись
                        }
                    }
                    LOGGER.log(Level.INFO, "Contact # " + contact.getContactId() + " was updated");
                } else {
                    //СОЗДАТЬ
                    photo = (Photo) updatePhotoService.service(photo, connection); //сначала фото
                    if (contact != null) {
                        contact.setPhotoId(photo.getPhotoId());
                    }
                    updateContactService.service(contact, connection); //потом контакт
                    if (photoItem != null && !photo.getName().isEmpty()) {
                        uploader.writePhoto(photoItem, photoPath, photo.getPhotoId()); //запись фото
                    }
                    for (Phone phone : phones) {
                        if (contact != null) {
                            phone.setContactId(contact.getContactId());
                        }
                        updatePhoneService.service(phone, connection); //потом телефон
                    }
                    for (int i = 0; i < attachments.size(); i++) {
                        if (contact != null) {
                            attachments.get(i).setContactId(contact.getContactId());
                        }
                        updateAttachmentService.service(attachments.get(i), connection); //потом вложение
                        uploader.writeFile(files.get(i), filePath, attachments.get(i).getAttachmentId()); //запись
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

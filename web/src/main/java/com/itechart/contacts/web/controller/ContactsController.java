package com.itechart.contacts.web.controller;

import com.itechart.contacts.domain.entity.impl.Attachment;
import com.itechart.contacts.domain.entity.impl.Contact;
import com.itechart.contacts.domain.entity.impl.Phone;
import com.itechart.contacts.domain.entity.impl.Photo;
import com.itechart.contacts.domain.exception.ServiceException;
import com.itechart.contacts.domain.service.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Class for contacts operations controller.
 * @author Marianna Patrusova
 * @version 1.0
 */
@WebServlet(urlPatterns = "/contacts/*")
public class ContactsController extends HttpServlet {

    private static final long serialVersionUID = 8362021663142387750L;
    private final static Logger LOGGER = LogManager.getLogger();
    private final static String CONTEXT = "/view_war/contacts/"; //artifact war, context /view_war fixme
    private final GetContactsService getContactsService = new GetContactsService();
    private final UpdateContactService updateContactService = new UpdateContactService();
    private final UpdateAttachmentService updateAttachmentService = new UpdateAttachmentService();
    private final UpdatePhotoService updatePhotoService = new UpdatePhotoService();
    private final UpdatePhoneService updatePhoneService = new UpdatePhoneService();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestUrl = request.getRequestURI();
        String id = requestUrl.substring(CONTEXT.length()); //get contact id from url if exists
        try {
            String json = getContactsService.service(id);
            response.setCharacterEncoding("UTF-8");  //response in ISO-8859-1, very bad for db data
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println(json);
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR, "Request process of finding contacts failed.");
            response.sendError(500);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Contact contact;
        Photo photo;
        List<Attachment> attachments;
        List<Phone> phones;
        //create all entities according with request
        String contactId = request.getParameter("contactId");
//        try {
//            if (!contactId.isEmpty()) { //обновить
//                long id = Long.parseLong(contactId);
//                updateContactService.service(contact); //передать энтити
//                updatePhotoService.service(photo);
//                updateAttachmentService.service(attachments);
//                updatePhoneService.service(phones);
//            } else { //создать
//                updateContactService.service(contactId);
//                updatePhotoService.service(photo);
//                updateAttachmentService.service(attachments);
//                updatePhoneService.service(phones);
//            }
//        } catch (ServiceException e) {
//            LOGGER.log(Level.ERROR, "Request process of finding contacts failed.");
//            response.sendError(500);
//        }
    }

}

package com.itechart.contacts.domain.service;

import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.dao.impl.AttachmentDao;
import com.itechart.contacts.domain.dao.impl.ContactDao;
import com.itechart.contacts.domain.dao.impl.PhoneDao;
import com.itechart.contacts.domain.dao.impl.PhotoDao;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.entity.impl.Attachment;
import com.itechart.contacts.domain.entity.impl.Contact;
import com.itechart.contacts.domain.entity.impl.Phone;
import com.itechart.contacts.domain.entity.impl.Photo;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import com.itechart.contacts.domain.util.PathBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import java.util.List;

/**
 * Class-service for finding contacts
 * @author Marianna Patrusova
 * @version 1.0
 */
public class GetContactsService {

    private final static Logger LOGGER = LogManager.getLogger();

    /**
     * Method: find contacts.
     * @return json with contact's data
     */
    public String service(String parameter) throws ServiceException {
        ContactDao contactDao = null;
        PhotoDao photoDao = null;
        PhoneDao phoneDao = null;
        AttachmentDao attachmentDao = null;
        try {
            contactDao = (ContactDao) DaoFactory.createDao(EntityType.CONTACT);
            photoDao = (PhotoDao) DaoFactory.createDao(EntityType.PHOTO);
            phoneDao = (PhoneDao) DaoFactory.createDao(EntityType.PHONE);
            attachmentDao = (AttachmentDao) DaoFactory.createDao(EntityType.ATTACHMENT);
            if (parameter !=null && !parameter.equals("")) {
                long id = Long.parseLong(parameter);
                Contact contact = (Contact) contactDao.findEntityById(id);
                if (contact != null) {
                    Photo photo = (Photo) photoDao.findEntityById(contact.getPhotoId());
                    List<AbstractEntity> alist = attachmentDao.findByContactId(id);
                    List<AbstractEntity> plist = phoneDao.findByContactId(id);
                    StringBuilder json = new StringBuilder("{\n");
                    createFullContactJson(contact, photo, json);
                    createAttachmentsJson(alist, json);
                    createPhonesJson(plist, json);
                    createCoreJson(contact, json);
                    LOGGER.log(Level.INFO, "User gets contact info: " + id);
                    return json.toString().substring(0, json.length() - 2);
                } else {
                    return "{}";
                }
            } else {
                List<AbstractEntity> contactList = contactDao.findAll();
                StringBuilder json = new StringBuilder("[\n");
                for (AbstractEntity entity: contactList) {
                    Contact contact = (Contact) entity;
                    json.append("{\n");
                    createCoreJson(contact, json);
                }
                return json.toString().substring(0, json.length() - 2) + "\n]";
            }
        } catch (DaoException | ClassNotFoundException e) {
            LOGGER.log(Level.ERROR,
                    "Exception in GetContactsService class while finding contact has occurred. ", e);
            throw new ServiceException(e);
        } finally {
            closeDaos(attachmentDao, contactDao, photoDao, phoneDao);
        }
    }

    //информация для списка контактов
    private void createCoreJson(Contact contact, StringBuilder json) {
        json.append("\"id\": ").append(JSONObject.numberToString(contact.getContactId())).append(",\n");
        json.append("\"name\": ").append(JSONObject.quote(contact.getName())).append(",\n");
        json.append("\"surname\": ").append(JSONObject.quote(contact.getSurname())).append(",\n");
        json.append("\"patronymic\": ").append(JSONObject.quote(contact.getPatronymic())).append(",\n");
        json.append("\"country\": ").append(JSONObject.quote(contact.getCountry())).append(",\n");
        json.append("\"city\": ").append(JSONObject.quote(contact.getCity())).append(",\n");
        json.append("\"address\": ").append(JSONObject.quote(contact.getAddress())).append(",\n");
        if (contact.getBirthday() == null) {
            json.append("\"birthday\": ").append(JSONObject.quote("")).append(",\n");
        } else {
            json.append("\"birthday\": ").append(JSONObject.valueToString(contact.getBirthday())).append(",\n");
        }
        json.append("\"work\": ").append(JSONObject.quote(contact.getWork())).append("\n");
        json.append("},\n");
    }

    //вся остальная информация по контакту с фото
    private void createFullContactJson(Contact contact, Photo photo, StringBuilder json) {
        json.append("\"gender\": ").append(JSONObject.quote(contact.getGender().getValue())).append(",\n");
        json.append("\"citizenship\": ").append(JSONObject.quote(contact.getCitizenship())).append(",\n");
        json.append("\"status\": ").append(JSONObject.quote(contact.getFamilyStatus())).append(",\n");
        json.append("\"website\": ").append(JSONObject.quote(contact.getWebsite())).append(",\n");
        json.append("\"email\": ").append(JSONObject.quote(contact.getEmail())).append(",\n");
        json.append("\"zipcode\": ").append(JSONObject.quote(contact.getZipcode())).append(",\n");
        json.append("\"idPhoto\": ").append(JSONObject.numberToString(contact.getPhotoId())).append(",\n");
        json.append("\"photo_path\": ").append(JSONObject.quote(PathBuilder.buildPath(photo))).append(",\n");
    }

    //вся информация по телефонам
    private void createPhonesJson(List<AbstractEntity> list, StringBuilder json) {
        if (!list.isEmpty()) {
            json.append("\"phones\": [\n");
            for (AbstractEntity entity : list) {
                Phone phone = (Phone) entity;
                json.append("{\n\"id_phone\": ").append(JSONObject.numberToString(phone.getPhoneId())).append(",\n");
                json.append("\"p_country\": ").append(JSONObject.quote(phone.getCountryCode())).append(",\n");
                json.append("\"p_operator\": ").append(JSONObject.quote(phone.getOperatorCode())).append(",\n");
                json.append("\"p_number\": ").append(JSONObject.quote(phone.getNumber())).append(",\n");
                json.append("\"p_type\": ").append(JSONObject.quote(phone.getType().getValue())).append(",\n");
                json.append("\"p_comments\": ").append(JSONObject.quote(phone.getComments())).append("},\n");
            }
            json.replace(json.length() - 3 , json.length(), "\n}\n");
            json.append("],\n");
        }
    }

    //вся информация по приложениям
    private void createAttachmentsJson(List<AbstractEntity> list, StringBuilder json) {
        if (!list.isEmpty()) {
            json.append("\"attachments\": [\n");
            for (AbstractEntity entity : list) {
                Attachment attachment = (Attachment) entity;
                json.append("{\n\"id_attachment\": ").append(JSONObject.numberToString(attachment.getAttachmentId())).append(",\n");
                json.append("\"a_path\": ").append(JSONObject.quote(PathBuilder.buildPath(attachment))).append(",\n");
                json.append("\"a_date\": ").append(JSONObject.valueToString(attachment.getLoadDate())).append(",\n");
                json.append("\"a_comments\": ").append(JSONObject.quote(attachment.getComments())).append("},\n");
            }
            json.replace(json.length() - 3 , json.length(), "\n}\n");
            json.append("],\n");
        }
    }

    //закрываем соединение
    private void closeDaos(AttachmentDao attachmentDao, ContactDao contactDao,
                           PhotoDao photoDao, PhoneDao phoneDao) {
        if (contactDao != null) {
            contactDao.exit();
        }
        if (photoDao != null) {
            photoDao.exit();
        }
        if (phoneDao != null) {
            phoneDao.exit();
        }
        if (attachmentDao != null) {
            attachmentDao.exit();
        }
    }

}


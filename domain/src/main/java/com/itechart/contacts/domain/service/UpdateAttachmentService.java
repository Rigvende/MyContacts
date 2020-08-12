package com.itechart.contacts.domain.service;

import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.dao.impl.AttachmentDao;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.entity.impl.Attachment;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;

/**
 * Class-service for creating / updating attachments
 * @author Marianna Patrusova
 * @version 1.0
 */
public class UpdateAttachmentService {

    private final static Logger LOGGER = LogManager.getLogger();

    //update
    public boolean service(long id, String name, String comments, Connection connection) throws ServiceException {
        Attachment attachment;
        AttachmentDao dao;
        try {
            dao = (AttachmentDao) DaoFactory.createDao(EntityType.ATTACHMENT, connection);
            attachment = (Attachment) dao.findEntityById(id);
            attachment.setComments(comments);
            attachment.setName(name);
            attachment.setPath(attachment.getPath());
            return dao.update(attachment);
        } catch (ClassNotFoundException | DaoException e) {
            LOGGER.log(Level.ERROR, "Cannot update attachment. Error has occurred. ", e);
            throw new ServiceException(e);
        }
    }

    //create
    public AbstractEntity service(Attachment attachment, Connection connection) throws ServiceException {
        AttachmentDao dao;
        try {
            dao = (AttachmentDao) DaoFactory.createDao(EntityType.ATTACHMENT, connection);
            attachment = (Attachment) dao.create(attachment);
            attachment.setPath("../attachments/" + attachment.getAttachmentId() + "/");
            if (dao.update(attachment)) {
                return attachment;
            } else {
                return null;
            }
        } catch (ClassNotFoundException | DaoException e) {
            LOGGER.log(Level.ERROR, "Cannot create attachment. Error has occurred. ", e);
            throw new ServiceException(e);
        }
    }

}

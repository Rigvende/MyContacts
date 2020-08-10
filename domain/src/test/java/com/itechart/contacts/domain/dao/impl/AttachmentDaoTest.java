package com.itechart.contacts.domain.dao.impl;

import com.itechart.contacts.domain.ConnectionTestInstance;
import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.entity.impl.Attachment;
import com.itechart.contacts.domain.exception.DaoException;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AttachmentDaoTest {

    private Connection connection;

    @Test
    void update() throws ClassNotFoundException, DaoException, SQLException {
        connection = ConnectionTestInstance.createConnection();
        AttachmentDao dao = (AttachmentDao) DaoFactory.createDao(EntityType.ATTACHMENT, connection);
        Attachment attachment = new Attachment();
        attachment.setAttachmentId(1);
        attachment.setName("log4j.txt");
        attachment.setComments("adsfaf");
        assertTrue(dao.update(attachment));
        connection.close();
    }

    @Test
    void findEntityById() throws SQLException, ClassNotFoundException, DaoException {
        connection = ConnectionTestInstance.createConnection();
        AttachmentDao dao = (AttachmentDao) DaoFactory.createDao(EntityType.ATTACHMENT, connection);
        Attachment attachment  = (Attachment)dao.findEntityById(1);
        assertNotNull(attachment);
        connection.close();
    }

    @Test
    void findByContactId() throws SQLException, ClassNotFoundException, DaoException {
        connection = ConnectionTestInstance.createConnection();
        AttachmentDao dao = (AttachmentDao) DaoFactory.createDao(EntityType.ATTACHMENT, connection);
        List<AbstractEntity> attachments = dao.findByContactId(1);
        assertFalse(attachments.isEmpty());
        connection.close();
    }

    @Test
    void findAll() throws SQLException, ClassNotFoundException, DaoException {
        connection = ConnectionTestInstance.createConnection();
        AttachmentDao dao = (AttachmentDao) DaoFactory.createDao(EntityType.ATTACHMENT, connection);
        List<AbstractEntity> list = dao.findAll();
        boolean flag = false;
        for (AbstractEntity entity: list) {
            Attachment attachment1 = (Attachment) entity;
            if (attachment1.getContactId() == 1) {
                flag = true;
                break;
            }
        }
        assertTrue(flag);
        connection.close();
    }

    @Test
    void createDelete() throws SQLException, ClassNotFoundException, DaoException {
        connection = ConnectionTestInstance.createConnection();
        AttachmentDao dao = (AttachmentDao) DaoFactory.createDao(EntityType.ATTACHMENT, connection);
        Attachment attachment = new Attachment();
        attachment.setContactId(1);
        attachment.setName("");
        attachment.setPath("");
        attachment.setLoadDate(LocalDate.now());
        attachment.setComments("");
        attachment = (Attachment) dao.create(attachment);
        assertTrue(attachment.getAttachmentId() != 0);
        assertTrue(dao.delete(attachment));
        connection.close();
    }

}
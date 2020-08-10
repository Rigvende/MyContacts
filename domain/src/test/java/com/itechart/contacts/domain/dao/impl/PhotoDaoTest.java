package com.itechart.contacts.domain.dao.impl;

import com.itechart.contacts.domain.ConnectionTestInstance;
import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.entity.impl.Photo;
import com.itechart.contacts.domain.exception.DaoException;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PhotoDaoTest {

    private Connection connection;

    @Test
    void createDelete() throws ClassNotFoundException, DaoException, SQLException {
        connection = ConnectionTestInstance.createConnection();
        PhotoDao dao = (PhotoDao) DaoFactory.createDao(EntityType.PHOTO, connection);
        Photo photo = new Photo();
        photo.setPath("");
        photo.setName("");
        photo = (Photo) dao.create(photo);
        assertTrue(photo.getPhotoId() != 0);
        assertTrue(dao.delete(photo));
        connection.close();
    }

    @Test
    void update() throws ClassNotFoundException, DaoException, SQLException {
        connection = ConnectionTestInstance.createConnection();
        PhotoDao dao = (PhotoDao) DaoFactory.createDao(EntityType.PHOTO, connection);
        Photo photo = new Photo();
        photo.setPhotoId(2);
        photo.setPath("");
        photo.setName("");
        assertTrue(dao.update(photo));
        connection.close();
    }

    @Test
    void findAll() throws ClassNotFoundException, DaoException, SQLException {
        connection = ConnectionTestInstance.createConnection();
        PhotoDao dao = (PhotoDao) DaoFactory.createDao(EntityType.PHOTO, connection);
        List<AbstractEntity> list = dao.findAll();
        boolean flag = false;
        for (AbstractEntity entity: list) {
            Photo photo = (Photo) entity;
            if (photo.getName().equals("")) {
                flag = true;
                break;
            }
        }
        assertTrue(flag);
        connection.close();
    }

    @Test
    void findEntityById() throws ClassNotFoundException, DaoException, SQLException {
        connection = ConnectionTestInstance.createConnection();
        PhotoDao dao = (PhotoDao) DaoFactory.createDao(EntityType.PHOTO, connection);
        assertNotNull(dao.findEntityById(2));
        connection.close();
    }
}
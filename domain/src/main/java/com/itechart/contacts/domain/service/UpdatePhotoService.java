package com.itechart.contacts.domain.service;

import com.itechart.contacts.domain.dao.DaoFactory;
import com.itechart.contacts.domain.dao.impl.PhotoDao;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.entity.impl.Photo;
import com.itechart.contacts.domain.exception.DaoException;
import com.itechart.contacts.domain.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;

/**
 * Class-service for creating / updating photos
 * @author Marianna Patrusova
 * @version 1.0
 */
public class UpdatePhotoService {

    private final static Logger LOGGER = LogManager.getLogger();

    //update
    public String service(long id, String name, Connection connection) throws ServiceException {
        Photo photo;
        PhotoDao dao;
        String oldName;
        try {
            dao = (PhotoDao) DaoFactory.createDao(EntityType.PHOTO, connection);
            photo = (Photo) dao.findEntityById(id);
            oldName = photo.getName();
            photo.setName(name);
            return dao.update(photo) ? oldName : null;
        } catch (ClassNotFoundException | DaoException e) {
            LOGGER.log(Level.ERROR, "Cannot update photo. Error has occurred. ", e);
            throw new ServiceException(e);
        }
    }

    //create
    public AbstractEntity service(Photo photo, Connection connection) throws ServiceException {
        PhotoDao dao;
        try {
            dao = (PhotoDao) DaoFactory.createDao(EntityType.PHOTO, connection);
            photo = (Photo) dao.create(photo);
            photo.setPath("../image/photos/" + photo.getPhotoId() + "/");
            if (dao.update(photo)) {
                return photo;
            } else {
                return null;
            }
        } catch (ClassNotFoundException | DaoException e) {
            LOGGER.log(Level.ERROR, "Cannot add photo. Error has occurred. ", e);
            throw new ServiceException(e);
        }
    }

}

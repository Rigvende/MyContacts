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

/**
 * Class-service for creating / updating photos
 * @author Marianna Patrusova
 * @version 1.0
 */
public class UpdatePhotoService {

    private final static Logger LOGGER = LogManager.getLogger();

    //update
    public boolean service(long id, String path) throws ServiceException {
        Photo photo;
        PhotoDao dao = null;
        try {
            dao = (PhotoDao) DaoFactory.createDao(EntityType.PHOTO);
            photo = (Photo) dao.findEntityById(id);
            photo.setPath(path);
            //todo
            //
            //
            //
            return dao.update(photo);
        } catch (ClassNotFoundException | DaoException e) {
            LOGGER.log(Level.ERROR, "Cannot update photo. Error has occurred. ", e);
            throw new ServiceException(e);
        } finally {
            if (dao != null) {
                dao.exit();
            }
        }
    }

    //create
    public AbstractEntity service(Photo photo) throws ServiceException {
        PhotoDao dao = null;
        try {
            dao = (PhotoDao) DaoFactory.createDao(EntityType.PHOTO);
            return dao.create(photo);
        } catch (ClassNotFoundException | DaoException e) {
            LOGGER.log(Level.ERROR, "Cannot create photo. Error has occurred. ", e);
            throw new ServiceException(e);
        } finally {
            if (dao != null) {
                dao.exit();
            }
        }
    }

}

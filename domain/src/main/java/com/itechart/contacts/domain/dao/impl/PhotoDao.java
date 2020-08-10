package com.itechart.contacts.domain.dao.impl;

import com.itechart.contacts.domain.dao.AbstractDao;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityBuilder;
import com.itechart.contacts.domain.entity.impl.Photo;
import com.itechart.contacts.domain.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for actions mostly with {@link Photo} using connections, statements and queries
 * according with DAO and database data
 * @author Marianna Patrusova
 * @version 1.0
 */
public class PhotoDao extends AbstractDao<AbstractEntity> {

    private final static Logger LOGGER = LogManager.getLogger();
    private final static String SQL_ADD_PHOTO =
            "INSERT INTO photos (photo_path, photo_name) VALUES (?, ?);";
    private final static String SQL_DELETE_PHOTO =
            "DELETE FROM photos WHERE id_photo = ?;";
    private final static String SQL_UPDATE_PHOTO =
            "UPDATE photos SET photo_path = ?, photo_name = ? WHERE id_photo = ?;";
    private static final String SQL_FIND_ALL_PHOTOS =
            "SELECT id_photo, photo_path, photo_name FROM photos;";
    private final static String SQL_FIND_PHOTO_BY_ID =
            "SELECT id_photo, photo_path, photo_name FROM photos WHERE id_photo = ?;";

    public PhotoDao(Connection connection) throws DaoException, ClassNotFoundException {
        super(connection);
    }

    @Override
    public AbstractEntity create(AbstractEntity entity) throws DaoException {
        Photo photo = (Photo) entity;
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(SQL_ADD_PHOTO, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, photo.getPath());
            preparedStatement.setString(2, photo.getName());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    photo.setPhotoId(resultSet.getLong(1));   //get generated key for the new photo
                } else {
                    photo = null;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"Cannot add photo. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return photo;
    }

    @Override
    public boolean delete(AbstractEntity entity) throws DaoException {
        boolean isDeleted = false;
        Photo photo = (Photo) entity;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_PHOTO)) {
            long id = photo.getPhotoId();
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            if (findEntityById(id) == null) {
                isDeleted = true;                           //check if photo is really deleted in db
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"Cannot delete photo. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return isDeleted;
    }

    @Override
    public boolean update(AbstractEntity entity) throws DaoException {
        Photo photo = (Photo) entity;
        boolean isUpdated = false;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PHOTO)) {
            preparedStatement.setString(1, photo.getPath());
            preparedStatement.setString(2, photo.getName());
            preparedStatement.setLong(3, photo.getPhotoId());
            int update = preparedStatement.executeUpdate();
            if (update == 1) {                              //check if row is updated (0 - false, 1 - true)
                isUpdated = true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"Cannot update photo. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return isUpdated;
    }

    @Override
    public List<AbstractEntity> findAll() throws DaoException {
        List<AbstractEntity> photos = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_PHOTOS)) {
                while (resultSet.next()) {
                    AbstractEntity photo = EntityBuilder.createPhoto(resultSet);
                    photos.add(photo);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"Cannot find photo list. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return photos;
    }

    @Override
    public AbstractEntity findEntityById(long id) throws DaoException {
        AbstractEntity photo = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PHOTO_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    photo = EntityBuilder.createPhoto(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR,"Cannot find photo by ID. Request to table failed. ", e);
            throw new DaoException(e);
        }
        return photo;
    }

}

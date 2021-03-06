package com.itechart.contacts.domain.dao;

import com.itechart.contacts.domain.dao.impl.*;
import com.itechart.contacts.domain.entity.AbstractEntity;
import com.itechart.contacts.domain.entity.EntityType;
import com.itechart.contacts.domain.exception.DaoException;
import java.sql.Connection;

/**
 * Class for creating instances of DAOs. "Fabric method" pattern is used
 * @author Marianna Patrusova
 * @version 1.0
 */
public class DaoFactory {

    private DaoFactory() {
    }

    /**
     * Method: create concrete child of AbstractDao
     * @param entityType is instance {@link EntityType} enum
     * @return {@link AbstractDao<AbstractEntity>}
     */
    public static AbstractDao<AbstractEntity> createDao(EntityType entityType, Connection connection)
                                                        throws DaoException, ClassNotFoundException {
        AbstractDao<AbstractEntity> dao = null;
        switch (entityType) {
            case ATTACHMENT:
                dao = new AttachmentDao(connection);
                break;
            case CONTACT:
                dao = new ContactDao(connection);
                break;
            case PHONE:
                dao = new PhoneDao(connection);
                break;
            case PHOTO:
                dao = new PhotoDao(connection);
                break;
        }
        return dao;
    }

}

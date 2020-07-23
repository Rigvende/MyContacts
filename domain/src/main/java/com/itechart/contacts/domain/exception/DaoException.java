package com.itechart.contacts.domain.exception;

/**
 * Class for handling exceptions on dao layer.
 * @author Marianna Patrusova
 * @version 1.0
 */
public class DaoException extends Exception {

    private static final long serialVersionUID = 5672657857807084509L;

    public DaoException() {
    }
    public DaoException(String message) {
        super(message);
    }
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
    public DaoException(Throwable cause) {
        super(cause);
    }

}

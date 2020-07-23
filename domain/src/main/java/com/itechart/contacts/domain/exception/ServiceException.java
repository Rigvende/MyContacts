package com.itechart.contacts.domain.exception;

/**
 * Class for handling exceptions on business logic layer.
 * @author Marianna Patrusova
 * @version 1.0
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = -4065508204705390042L;

    public ServiceException() {
    }
    public ServiceException(String message) {
        super(message);
    }
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    public ServiceException(Throwable cause) {
        super(cause);
    }

}

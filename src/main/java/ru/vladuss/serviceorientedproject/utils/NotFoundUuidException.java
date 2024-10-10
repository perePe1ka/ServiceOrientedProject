package ru.vladuss.serviceorientedproject.utils;

public class NotFoundUuidException extends Exception{
    public NotFoundUuidException() {
        super();
    }

    public NotFoundUuidException(String message) {
        super(message);
    }

    public NotFoundUuidException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundUuidException(Throwable cause) {
        super(cause);
    }

    public NotFoundUuidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

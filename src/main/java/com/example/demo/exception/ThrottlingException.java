package com.example.demo.exception;

public class ThrottlingException extends RuntimeException {

    public ThrottlingException() {
        super();
    }

    public ThrottlingException(String message) {
        super(message);
    }

    public ThrottlingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThrottlingException(Throwable cause) {
        super(cause);
    }

    protected ThrottlingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

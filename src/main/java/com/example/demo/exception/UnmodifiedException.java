package com.example.demo.exception;

public class UnmodifiedException extends RuntimeException {

    public UnmodifiedException() {
        this("Unmodified object!");
    }

    public UnmodifiedException(String message) {
        super(message);
    }

    public UnmodifiedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnmodifiedException(Throwable cause) {
        super(cause);
    }

    public UnmodifiedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
package ru.astondevs.deliveryservice.exception;

public class DuplicateException extends RuntimeException {
    public DuplicateException() {
    }

    public DuplicateException(String message) {
        super(message);
    }
}

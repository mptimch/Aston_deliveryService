package ru.astondevs.deliveryservice.exception;

public class NotFoundModelException extends RuntimeException {
    public NotFoundModelException(String message) {
        super(message);
    }
}
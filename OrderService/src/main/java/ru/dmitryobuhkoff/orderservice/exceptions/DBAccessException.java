package ru.dmitryobuhkoff.orderservice.exceptions;

public class DBAccessException extends CustomRuntimeException {
    public DBAccessException(String message) {
        super(message);
    }
}

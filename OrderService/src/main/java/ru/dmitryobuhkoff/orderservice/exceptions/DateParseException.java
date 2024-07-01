package ru.dmitryobuhkoff.orderservice.exceptions;

public class DateParseException extends CustomRuntimeException{

    public DateParseException(String message) {
        super(message);
    }
}

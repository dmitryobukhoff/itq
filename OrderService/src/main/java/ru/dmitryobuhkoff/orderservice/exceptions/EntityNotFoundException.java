package ru.dmitryobuhkoff.orderservice.exceptions;

public class EntityNotFoundException extends CustomRuntimeException{

    public EntityNotFoundException(String message) {
        super(message);
    }
}

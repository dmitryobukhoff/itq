package ru.dmitryobukhoff.numbergenerator.exception;

public class DbAccessException extends CustomRuntimeException{
    public DbAccessException(String message) {
        super(message);
    }
}

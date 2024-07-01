package ru.dmitryobuhkoff.orderservice.exceptions;

import java.time.LocalDateTime;

public class ApiCallException extends CustomRuntimeException{
    public ApiCallException(String message){
        super(message);
    }
    public ApiCallException(String message, LocalDateTime time) {
        super(message, time);
    }
}

package ru.dmitryobuhkoff.orderservice.exceptions;

import java.time.LocalDateTime;

public abstract class CustomRuntimeException extends RuntimeException{
    private String message;
    private LocalDateTime timestamp;

    public CustomRuntimeException(String message){
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public CustomRuntimeException(String message, LocalDateTime timestamp){
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage(){
        return message;
    }
    public LocalDateTime getTimestamp(){
        return timestamp;
    }
}

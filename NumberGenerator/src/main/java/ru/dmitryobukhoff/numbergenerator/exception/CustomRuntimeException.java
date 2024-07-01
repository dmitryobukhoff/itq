package ru.dmitryobukhoff.numbergenerator.exception;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class CustomRuntimeException extends RuntimeException{
    private String message;
    private LocalDateTime timestamp;

    public CustomRuntimeException(String message){
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }
}

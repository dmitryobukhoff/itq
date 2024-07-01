package ru.dmitryobukhoff.numbergenerator.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.dmitryobukhoff.numbergenerator.exception.DbAccessException;
import ru.dmitryobukhoff.numbergenerator.exception.NotUniqueOrderNumberException;
import ru.dmitryobukhoff.numbergenerator.exception.SHA256AlgorithmException;
import ru.dmitryobukhoff.numbergenerator.util.ExceptionFactory;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerHandler {

    private final ExceptionFactory exceptionFactory;

    @ExceptionHandler(NotUniqueOrderNumberException.class)
    public ResponseEntity<?> handleNotUniqNumberException(NotUniqueOrderNumberException exception, HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(exceptionFactory.getErrorResponse(exception, httpServletRequest.getServletPath()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DbAccessException.class)
    public ResponseEntity<?> handleDbAccessException(DbAccessException exception, HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(exceptionFactory.getErrorResponse(exception, httpServletRequest.getServletPath()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SHA256AlgorithmException.class)
    public ResponseEntity<?> handleSHA256AlgorithmException(SHA256AlgorithmException exception, HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(exceptionFactory.getErrorResponse(exception, httpServletRequest.getServletPath()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

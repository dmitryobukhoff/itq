package ru.dmitryobuhkoff.orderservice.exceptions.handler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.dmitryobuhkoff.orderservice.exceptions.ApiCallException;
import ru.dmitryobuhkoff.orderservice.exceptions.DBAccessException;
import ru.dmitryobuhkoff.orderservice.exceptions.DateParseException;
import ru.dmitryobuhkoff.orderservice.exceptions.EntityNotFoundException;
import ru.dmitryobuhkoff.orderservice.model.dto.response.ApiErrorResponse;
import ru.dmitryobuhkoff.orderservice.util.ExceptionFactory;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionApiHandler {

    private final ExceptionFactory exceptionFactory;

    @ExceptionHandler(DateParseException.class)
    public ResponseEntity<ApiErrorResponse> handleDateParseException(DateParseException exception, HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(exceptionFactory.getApiResponse(exception, httpServletRequest.getServletPath()), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DBAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleDBAccessException(DBAccessException exception, HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(exceptionFactory.getApiResponse(exception, httpServletRequest.getServletPath()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(EntityNotFoundException exception, HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(exceptionFactory.getApiResponse(exception, httpServletRequest.getServletPath()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiCallException.class)
    public ResponseEntity<ApiErrorResponse> handleApiCallException(ApiCallException exception, HttpServletRequest httpServletRequest){
        return new ResponseEntity<>(exceptionFactory.getApiResponse(exception, httpServletRequest.getServletPath()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

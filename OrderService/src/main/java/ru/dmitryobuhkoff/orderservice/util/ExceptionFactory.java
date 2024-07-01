package ru.dmitryobuhkoff.orderservice.util;

import org.springframework.stereotype.Component;
import ru.dmitryobuhkoff.orderservice.exceptions.CustomRuntimeException;
import ru.dmitryobuhkoff.orderservice.model.dto.response.ApiErrorResponse;

@Component
public class ExceptionFactory {

    public ApiErrorResponse getApiResponse(CustomRuntimeException exception, String path){
        return ApiErrorResponse.builder()
                .timestamp(exception.getTimestamp())
                .message(exception.getMessage())
                .path(path)
                .build();
    }
}

package ru.dmitryobukhoff.numbergenerator.util;

import org.springframework.stereotype.Component;
import ru.dmitryobukhoff.numbergenerator.exception.CustomRuntimeException;
import ru.dmitryobukhoff.numbergenerator.model.dto.response.ErrorResponse;

@Component
public class ExceptionFactory{

    public ErrorResponse getErrorResponse(CustomRuntimeException customRuntimeException, String path){
        return ErrorResponse.builder()
                .message(customRuntimeException.getMessage())
                .timestamp(customRuntimeException.getTimestamp())
                .path(path)
                .build();
    }
}

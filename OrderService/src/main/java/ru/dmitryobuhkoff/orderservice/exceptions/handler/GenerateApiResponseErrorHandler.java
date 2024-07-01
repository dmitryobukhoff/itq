package ru.dmitryobuhkoff.orderservice.exceptions.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import ru.dmitryobuhkoff.orderservice.exceptions.ApiCallException;
import ru.dmitryobuhkoff.orderservice.util.ExceptionFactory;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GenerateApiResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper mapper = new ObjectMapper();
    private final ExceptionFactory exceptionFactory;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        ApiCallException apiCallException = mapper.readValue(response.getBody(), ApiCallException.class);
        throw new ApiCallException(apiCallException.getMessage(), apiCallException.getTimestamp());
    }
}

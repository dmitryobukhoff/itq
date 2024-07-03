package ru.dmitryobuhkoff.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.dmitryobuhkoff.orderservice.exceptions.ApiCallException;
import ru.dmitryobuhkoff.orderservice.model.dto.response.ApiCallResponse;
import ru.dmitryobuhkoff.orderservice.service.ApiCallService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiCallServiceImpl implements ApiCallService {
    private static final String URL = "http://number-generator:8081/api/v1/generating/generate";
    private final RestTemplate restTemplate;
    @Override
    public ApiCallResponse requestForNumber(UUID uuid) throws RestClientException {
        String url = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("uuid", uuid)
                .encode()
                .toUriString();
        try {
            return restTemplate.getForObject(url, ApiCallResponse.class);
        }catch (RuntimeException exception){
            log.error(exception.getMessage());
            throw new ApiCallException("Number Generate service not connected");
        }
    }
}

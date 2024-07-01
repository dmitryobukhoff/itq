package ru.dmitryobuhkoff.orderservice.service;

import org.springframework.web.client.RestClientException;
import ru.dmitryobuhkoff.orderservice.model.dto.response.ApiCallResponse;

import java.util.UUID;

public interface ApiCallService {
    ApiCallResponse requestForNumber(UUID uuid) throws RestClientException;
}

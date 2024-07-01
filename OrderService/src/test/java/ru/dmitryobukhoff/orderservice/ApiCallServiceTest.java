package ru.dmitryobukhoff.orderservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import ru.dmitryobuhkoff.orderservice.exceptions.ApiCallException;
import ru.dmitryobuhkoff.orderservice.model.dto.response.ApiCallResponse;
import ru.dmitryobuhkoff.orderservice.service.impl.ApiCallServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ApiCallServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ApiCallServiceImpl apiCallService;

    @Test
    void testRequestForNumberNoException(){
        UUID uuid = UUID.randomUUID();

        ApiCallResponse apiCallResponse = new ApiCallResponse("abced20240107");

        when(restTemplate.getForObject(any(String.class), eq(ApiCallResponse.class))).thenReturn(apiCallResponse);

        ApiCallResponse response = apiCallService.requestForNumber(uuid);

        assertEquals(response, apiCallResponse);
        verify(restTemplate, times(1)).getForObject(any(String.class), eq(ApiCallResponse.class));
    }

    @Test
    void testRequestForNumberWithException(){
        UUID uuid = UUID.randomUUID();

        when(restTemplate.getForObject(any(String.class), eq(ApiCallResponse.class))).thenThrow(new RuntimeException());

        assertThrows(ApiCallException.class, () -> apiCallService.requestForNumber(uuid));

        verify(restTemplate, times(1)).getForObject(any(String.class), eq(ApiCallResponse.class));
    }
}
